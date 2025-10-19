package edu.ucne.jugadorestictactoe.domain.jugadores

import app.cash.turbine.test
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.EliminarJugadorUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class EliminarJugadorUseCaseTest {

    private lateinit var repository: JugadorRepository
    private lateinit var useCase: EliminarJugadorUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = EliminarJugadorUseCase(repository)
    }

    @Test
    fun `calls repository delete with jugador`() = runTest {
        val jugador = Jugador(id = 1, nombre = "Juan", partidas = 1)

        coEvery { repository.delete(jugador) } just runs

        useCase(jugador)

        coVerify { repository.delete(jugador) }
    }
}
