package edu.ucne.jugadorestictactoe.domain.jugadores

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ValidarJugadorUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals


class ValidarJugadorUseCaseTest {

    private lateinit var repository: JugadorRepository
    private lateinit var useCase: ValidarJugadorUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ValidarJugadorUseCase(repository)
    }

    @Test
    fun `validation succeeds with valid jugador`() = runTest {
        val jugador = Jugador(id = 1, nombre = "Juan", partidas = 5)
        coEvery { repository.getAll() } returns emptyList()

        val result = useCase(jugador)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `validation fails when nombre is blank`() = runTest {
        val jugador = Jugador(id = 1, nombre = "", partidas = 5)
        val result = useCase(jugador)

        assertTrue(result.isFailure)
        assertEquals("Nombre vacío o partidas negativas", result.exceptionOrNull()?.message)
    }

    @Test
    fun `validation fails when partidas are negative`() = runTest {
        val jugador = Jugador(id = 1, nombre = "Juan", partidas = -1)
        val result = useCase(jugador)

        assertTrue(result.isFailure)
        assertEquals("Nombre vacío o partidas negativas", result.exceptionOrNull()?.message)
    }

    @Test
    fun `validation fails when nombre is repeated`() = runTest {
        val jugador = Jugador(id = 2, nombre = "Ana", partidas = 3)
        val existingPlayers = listOf(
            Jugador(id = 1, nombre = "Ana", partidas = 2)
        )
        coEvery { repository.getAll() } returns existingPlayers

        val result = useCase(jugador)

        assertTrue(result.isFailure)
        assertEquals("Ya existe un jugador con ese nombre", result.exceptionOrNull()?.message)
    }
}
