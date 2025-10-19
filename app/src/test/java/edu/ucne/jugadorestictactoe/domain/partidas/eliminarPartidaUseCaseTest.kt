package edu.ucne.jugadorestictactoe.domain.partidas

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.EliminarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.EliminarPartidaUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class EliminarPartidaUseCaseTest {

    private lateinit var repository: PartidaRepository
    private lateinit var useCase: EliminarPartidaUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = EliminarPartidaUseCase(repository)
    }

    @Test
    fun `calls repository delete with partida`() = runTest {
        val partida = Partida(
        id = 1,
        fecha = "2025-09-24",
        jugador1 = 101,
        jugador2 = 102,
        ganadorId = 101,
        esFinalizada = true
    )

        coEvery { repository.delete(partida) } just runs

        useCase(partida)

        coVerify { repository.delete(partida) }
    }
}
