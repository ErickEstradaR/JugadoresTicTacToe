package edu.ucne.jugadorestictactoe.domain.partidas

import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ObtenerPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ObtenerPartidasUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class ObtenerPartidaUseCaseTest {

    private lateinit var repository: PartidaRepository
    private lateinit var useCase: ObtenerPartidaUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = ObtenerPartidaUseCase(repository)
    }

    @Test
    fun `invoke returns partida when found`() = runTest {
        val partida = Partida(
            id = 1,
            fecha = "2025-09-24",
            jugador1 = 101,
            jugador2 = 102,
            ganadorId = 101,
            esFinalizada = true
        )
        coEvery { repository.find(1) } returns partida

        val result = useCase(1)

        assertEquals(partida, result)
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
