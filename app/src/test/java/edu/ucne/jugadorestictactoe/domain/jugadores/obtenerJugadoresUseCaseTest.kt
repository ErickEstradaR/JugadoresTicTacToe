package edu.ucne.jugadorestictactoe.domain.jugadores

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import app.cash.turbine.test
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ObtenerJugadoresUseCase

class ObtenerJugadoresUseCaseTest {

    private lateinit var repository: JugadorRepository
    private lateinit var useCase: ObtenerJugadoresUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = ObtenerJugadoresUseCase(repository)
    }

    @Test
    fun `invoke returns all jugadores from repository`() = runTest {
        val jugadores = listOf(
            Jugador(jugadorId = 1, nombre = "Juan",partidas =5),
            Jugador(jugadorId = 2, nombre = "Ana",partidas =5)
        )
        val flow: Flow<List<Jugador>> = flowOf(jugadores)

        coEvery { repository.getAllFlow() } returns flow

        useCase().test {
            val emitted = awaitItem()
            assert(emitted == jugadores)
            awaitComplete()
        }
        verify { repository.getAllFlow() }
    }
}
