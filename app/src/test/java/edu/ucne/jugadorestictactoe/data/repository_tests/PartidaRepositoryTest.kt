package edu.ucne.jugadorestictactoe.data.repository_tests


import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import edu.ucne.jugadorestictactoe.data.local.partidas.Dao.PartidaDao
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity
import edu.ucne.jugadorestictactoe.data.local.repository.PartidaRepositoryImpl
import edu.ucne.jugadorestictactoe.domain.model.Partida
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
class PartidaRepositoryImplTest {

    private lateinit var dao: PartidaDao
    private lateinit var repository: PartidaRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = PartidaRepositoryImpl(dao)
    }

    @Test
    fun getAllFlow_emitsMappedDomainModels() = runTest {
        val shared = MutableSharedFlow<List<PartidaEntity>>()
        every { dao.getAllPartidas() } returns shared

        val job = launch {
            repository.getAllFlow().test {
                shared.emit(listOf(PartidaEntity(1, "2025-09-24", 1, 2, 1, true)))
                val first = awaitItem()
                assertThat(first).containsExactly(
                    Partida(id = 1, fecha = "2025-09-24", jugador1 = 1, jugador2 = 2, ganadorId = 1, esFinalizada = true)
                )

                shared.emit(listOf(
                    PartidaEntity(2, "2025-09-25", 3, 4, 4, false),
                    PartidaEntity(3, "2025-09-26", 5, 6, null, false)
                ))
                val second = awaitItem()
                assertThat(second).containsExactly(
                    Partida(id = 2, fecha = "2025-09-25", jugador1 = 3, jugador2 = 4, ganadorId = 4, esFinalizada = false),
                    Partida(id = 3, fecha = "2025-09-26", jugador1 = 5, jugador2 = 6, ganadorId = null, esFinalizada = false)
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
    }

    @Test
    fun find_returnsMappedDomainModel_whenEntityExists() = runTest {
        coEvery { dao.findPartida(5) } returns PartidaEntity(5, "2025-09-20", 1, 2, 2, true)

        val result = repository.find(5)

        assertThat(result).isEqualTo(Partida(id = 5, fecha = "2025-09-20", jugador1 = 1, jugador2 = 2, ganadorId = 2, esFinalizada = true))
    }

    @Test
    fun find_returnsNull_whenEntityMissing() = runTest {
        coEvery { dao.findPartida(42) } returns null

        val result = repository.find(42)

        assertThat(result).isNull()
    }

    @Test
    fun save_callsDaoInsert_andReturnsTrueWhenSuccess() = runTest {
        coEvery { dao.savePartida(any()) } returns Unit
        val partida = Partida(id = 10, fecha = "2025-09-22", jugador1 = 1, jugador2 = 2, ganadorId = 1, esFinalizada = true)

        val success = repository.save(partida)

        assertThat(success).isTrue()
        coVerify { dao.savePartida(PartidaEntity(10, "2025-09-22", 1, 2, 1, true)) }
    }

    @Test
    fun delete_callsDaoDelete() = runTest {
        coEvery { dao.deletePartida(any()) } just runs
        val partida = Partida(id = 12, fecha = "2025-09-23", jugador1 = 7, jugador2 = 8, ganadorId = null, esFinalizada = false)

        repository.delete(partida)

        coVerify { dao.deletePartida(PartidaEntity(12, "2025-09-23", 7, 8, null, false)) }
    }

    @Test
    fun getAll_returnsMappedDomainModels() = runTest {
        coEvery { dao.getAllPartidas() } returns flowOf(
            listOf(
                PartidaEntity(1, "2025-09-20", 1, 2, 1, true),
                PartidaEntity(2, "2025-09-21", 3, 4, null, false)
            )
        )

        val result = repository.getAllFlow().first()

        assertThat(result).isEqualTo(
            listOf(
                Partida(id = 1, fecha = "2025-09-20", jugador1 = 1, jugador2 = 2, ganadorId = 1, esFinalizada = true),
                Partida(id = 2, fecha = "2025-09-21", jugador1 = 3, jugador2 = 4, ganadorId = null, esFinalizada = false)
            )
        )
    }
}
