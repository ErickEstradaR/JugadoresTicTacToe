package edu.ucne.jugadorestictactoe.data.repository_tests

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import edu.ucne.jugadorestictactoe.data.local.logros.dao.LogroDao
import edu.ucne.jugadorestictactoe.data.local.logros.entity.LogroEntity
import edu.ucne.jugadorestictactoe.data.local.repository.LogroRepositoryImpl
import edu.ucne.jugadorestictactoe.domain.model.Logro
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LogroRepositoryImplTest {

    private lateinit var dao: LogroDao
    private lateinit var repository: LogroRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = LogroRepositoryImpl(dao)
    }

    @Test
    fun getAllFlow_emitsMappedDomainModels() = runTest {
        val shared = MutableSharedFlow<List<LogroEntity>>()
        every { dao.getAllLogros() } returns shared

        val job = launch {
            repository.getAllFlow().test {
                shared.emit(listOf(LogroEntity(1, "Campeón", "Ganó el torneo")))
                val first = awaitItem()
                assertThat(first).containsExactly(
                    Logro(id = 1, nombre = "Campeón", descripcion = "Ganó el torneo")
                )

                shared.emit(listOf(
                    LogroEntity(2, "Invicto", "No perdió ninguna partida"),
                    LogroEntity(3, "Máximo Goleador", "Anotó más goles")
                ))
                val second = awaitItem()
                assertThat(second).containsExactly(
                    Logro(id = 2, nombre = "Invicto", descripcion = "No perdió ninguna partida"),
                    Logro(id = 3, nombre = "Máximo Goleador", descripcion = "Anotó más goles")
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
    }

    @Test
    fun find_returnsMappedDomainModel_whenEntityExists() = runTest {
        coEvery { dao.findLogro(5) } returns LogroEntity(5, "Histórico", "Jugó 100 partidos")

        val result = repository.find(5)

        assertThat(result).isEqualTo(Logro(id = 5, nombre = "Histórico", descripcion = "Jugó 100 partidos"))
    }

    @Test
    fun find_returnsNull_whenEntityMissing() = runTest {
        coEvery { dao.findLogro(42) } returns null

        val result = repository.find(42)

        assertThat(result).isNull()
    }

    @Test
    fun save_callsDaoInsert_andReturnsTrueWhenSuccess() = runTest {
        coEvery { dao.saveLogro(any()) } returns Unit
        val logro = Logro(id = 10, nombre = "Leyenda", descripcion = "Ganó 10 campeonatos")

        val success = repository.save(logro)

        assertThat(success).isTrue()
        coVerify { dao.saveLogro(LogroEntity(10, "Leyenda", "Ganó 10 campeonatos")) }
    }

    @Test
    fun delete_callsDaoDelete() = runTest {
        coEvery { dao.deleteLogro(any()) } just runs
        val logro = Logro(id = 12, nombre = "Estratega", descripcion = "Asistió 50 goles")

        repository.delete(logro)

        coVerify { dao.deleteLogro(LogroEntity(12, "Estratega", "Asistió 50 goles")) }
    }

    @Test
    fun getAll_returnsMappedDomainModels() = runTest {
        coEvery { dao.getAllLogros() } returns flowOf(
            listOf(
                LogroEntity(1, "Campeón", "Ganó el torneo"),
                LogroEntity(2, "Invicto", "No perdió ninguna partida")
            )
        )

        val result = repository.getAllFlow().first()

        assertThat(result).isEqualTo(
            listOf(
                Logro(id = 1, nombre = "Campeón", descripcion = "Ganó el torneo"),
                Logro(id = 2, nombre = "Invicto", descripcion = "No perdió ninguna partida")
            )
        )
    }
}
