package edu.ucne.jugadorestictactoe.domain.jugadores

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ObtenerJugadorUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ObtenerJugadorUseCaseTest {

    private lateinit var repository: JugadorRepository
    private lateinit var useCase: ObtenerJugadorUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = ObtenerJugadorUseCase(repository)
    }

    @Test
    fun `invoke returns jugador when found`() = runTest {
        val jugador = Jugador(jugadorId = 1, nombre = "Juan" , partidas =5)
        coEvery { repository.find(1) } returns jugador

        val result = useCase(1)

        assertEquals(jugador, result)
        coVerify { repository.find(1) }
    }

    @Test
    fun `invoke returns null when jugador not found`() = runTest {
        coEvery { repository.find(2) } returns null

        val result = useCase(2)

        assertNull(result)
        coVerify { repository.find(2) }
    }
}
