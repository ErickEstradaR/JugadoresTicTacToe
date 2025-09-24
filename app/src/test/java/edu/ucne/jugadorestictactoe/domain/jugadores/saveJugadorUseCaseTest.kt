package edu.ucne.jugadorestictactoe.domain.jugadores

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.GuardarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ValidarJugadorUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GuardarJugadorUseCaseTest {

    private lateinit var repository: JugadorRepository
    private lateinit var validarJugador: ValidarJugadorUseCase
    private lateinit var useCase: GuardarJugadorUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        validarJugador = mockk()
        useCase = GuardarJugadorUseCase(repository, validarJugador)
    }

    @Test
    fun `invoke saves jugador when validation succeeds`() = runTest {
        val jugador = Jugador(id = 1, nombre = "Juan",partidas = 5)
        coEvery { validarJugador(jugador) } returns Result.success(Unit)
        coEvery { repository.save(jugador) } returns true

        val result = useCase(jugador)
        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())

        coVerify { validarJugador(jugador) }
        coVerify { repository.save(jugador) }
    }

    @Test
    fun `invoke returns failure when validation fails`() = runTest {
        val jugador = Jugador(id = 2, nombre = "", partidas = 5)

        val exception = IllegalArgumentException("Nombre no puede estar vacío")
        coEvery { validarJugador(jugador) } returns Result.failure(exception)

        val result = useCase(jugador)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())

        coVerify { validarJugador(jugador) }
        coVerify(exactly = 0) { repository.save(jugador) }
    }
}
