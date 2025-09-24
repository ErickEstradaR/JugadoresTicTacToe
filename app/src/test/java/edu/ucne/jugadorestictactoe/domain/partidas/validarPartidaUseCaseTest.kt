package edu.ucne.jugadorestictactoe.domain.partidas

import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ValidarPartidaUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValidarPartidaUseCaseTest {

    private lateinit var useCase: ValidarPartidaUseCase

    @Before
    fun setup() {
        useCase = ValidarPartidaUseCase()
    }

    @Test
    fun `validation fails when players are the same`() = runTest {
        val partida = Partida(
            id = 1,
            fecha = "2025-09-24",
            jugador1 = 101,
            jugador2 = 101,
            ganadorId = null,
            esFinalizada = false
        )

        val result = useCase(partida)

        assertTrue(result.isFailure)
        assertEquals("Un jugador no puede jugar contra sí mismo", result.exceptionOrNull()?.message)
    }

    @Test
    fun `validation fails when winner is not a player`() = runTest {
        val partida = Partida(
            id = 2,
            fecha = "2025-09-24",
            jugador1 = 101,
            jugador2 = 102,
            ganadorId = 999,
            esFinalizada = true
        )

        val result = useCase(partida)

        assertTrue(result.isFailure)
        assertEquals("El ganador debe ser uno de los jugadores de la partida",
            result.exceptionOrNull()?.message)
    }

    @Test
    fun `validation succeeds for a valid partida`() = runTest {
        val partida = Partida(
            id = 3,
            fecha = "2025-09-24",
            jugador1 = 101,
            jugador2 = 102,
            ganadorId = 101,
            esFinalizada = true
        )

        val result = useCase(partida)

        assertTrue(result.isSuccess)
    }
}
