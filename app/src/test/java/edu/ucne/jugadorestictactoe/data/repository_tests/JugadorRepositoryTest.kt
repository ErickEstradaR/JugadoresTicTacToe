package edu.ucne.jugadorestictactoe.data.repository_tests

import app.cash.turbine.test
import edu.ucne.jugadorestictactoe.data.local.jugadores.Dao.JugadorDao
import edu.ucne.jugadorestictactoe.data.local.jugadores.Entities.JugadorEntity
import edu.ucne.jugadorestictactoe.data.local.repository.JugadorRepositoryImpl
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import com.google.common.truth.Truth.assertThat
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
class JugadorRepositoryImplTest {

    private lateinit var dao: JugadorDao
    private lateinit var repository: JugadorRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = JugadorRepositoryImpl(dao)
    }

    @Test
    fun getAllFlow_emitsMappedDomainModels() = runTest {
        val shared = MutableSharedFlow<List<JugadorEntity>>()
        every { dao.getAll() } returns shared

        val job = launch {
            repository.getAllFlow().test {

                shared.emit(listOf(JugadorEntity(jugadorId = 1, nombres = "Messi", partidas = 1)))
                val first = awaitItem()
                assertThat(first).containsExactly(Jugador(id = 1, nombre = "Messi", partidas = 1))


                shared.emit(
                    listOf(
                        JugadorEntity(jugadorId = 2, nombres = "CR7" ,partidas = 1),
                        JugadorEntity(jugadorId = 3, nombres = "Neymar", partidas = 1)
                    )
                )
                val second = awaitItem()
                assertThat(second).containsExactly(
                    Jugador(id = 2, nombre = "CR7", partidas = 1),
                    Jugador(id = 3, nombre = "Neymar" , partidas = 1)
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
    }

    @Test
    fun find_returnsMappedDomainModel_whenEntityExists() = runTest {
        coEvery { dao.find(5) } returns JugadorEntity(jugadorId = 5, nombres = "Xavi" , partidas = 1)

        val result = repository.find(5)

        assertThat(result).isEqualTo(Jugador(id = 5, nombre = "Xavi", partidas = 1))
    }

    @Test
    fun find_returnsNull_whenEntityMissing() = runTest {
        coEvery { dao.find(42) } returns null

        val result = repository.find(42)

        assertThat(result).isNull()
    }

    @Test
    fun save_callsDaoInsert_andReturnsTrueWhenSuccess() = runTest {
        coEvery { dao.save(any()) } returns Unit
        val jugador = Jugador(id = 10, nombre = "Iniesta",partidas = 1)

        val success = repository.save(jugador)

        assertThat(success).isTrue()
        coVerify { dao.save(JugadorEntity(jugadorId = 10, nombres = "Iniesta", partidas = 1)) }
    }

    @Test
    fun delete_callsDaoDelete() = runTest {
        coEvery { dao.delete(any()) } just runs
        val jugador = Jugador(id = 12, nombre = "Puyol",partidas = 1)

        repository.delete(jugador)

        coVerify { dao.delete(JugadorEntity(jugadorId = 12, nombres = "Puyol",partidas = 1)) }
    }

    @Test
    fun getAll_returnsMappedDomainModels() = runTest {
        // Arrange
        coEvery { dao.getAll() } returns flowOf(
            listOf(
                JugadorEntity(jugadorId = 1, nombres = "Messi", partidas = 1),
                JugadorEntity(jugadorId = 2, nombres = "CR7", partidas = 1)
            )
        )
        val result = repository.getAllFlow().first()
        assertThat(result).isEqualTo(
            listOf(
                Jugador(id = 1, nombre = "Messi", partidas = 1),
                Jugador(id = 2, nombre = "CR7", partidas = 1)
            )
        )
    }
}
