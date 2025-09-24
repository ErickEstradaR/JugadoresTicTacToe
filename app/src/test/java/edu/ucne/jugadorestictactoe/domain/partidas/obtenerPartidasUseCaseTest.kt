package edu.ucne.jugadorestictactoe.domain.partidas

import app.cash.turbine.test
import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ObtenerPartidasUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class ObtenerPartidasUseCaseTest {

    private lateinit var repository: PartidaRepository
    private lateinit var useCase: ObtenerPartidasUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = ObtenerPartidasUseCase(repository)
    }

    @Test
    fun `invoke returns all partidas from repository`() = runTest {
        val partidas = listOf(
            Partida
                (id = 1,
                fecha = "2025-09-24",
                jugador1 = 101,
                jugador2 = 102,
                ganadorId = 101,
                esFinalizada = true),
            Partida(id = 2,
                fecha = "2025-09-25",
                jugador1 = 103,
                jugador2 = 104,
                ganadorId = null,
                esFinalizada = false)
        )

        val flow: Flow<List<Partida>> = flowOf(partidas)

        coEvery { repository.getAllFlow() } returns flow

        useCase().test {
            val emitted = awaitItem()
            assertEquals(partidas, emitted)
            awaitComplete()
        }

        coVerify { repository.getAllFlow() }
    }
}


