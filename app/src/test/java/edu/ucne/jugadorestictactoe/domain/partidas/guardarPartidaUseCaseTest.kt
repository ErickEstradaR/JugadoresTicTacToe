package edu.ucne.jugadorestictactoe.domain.partidas

import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.GuardarPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ValidarPartidaUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class guardarPartidaUseCaseTest {

    private lateinit var repository: PartidaRepository
    private lateinit var validarPartida: ValidarPartidaUseCase
    private lateinit var useCase: GuardarPartidaUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        validarPartida = mockk()
        useCase = GuardarPartidaUseCase(repository, validarPartida)
    }

    @Test
    fun `invoke saves partida when validation succeeds`() = runTest {
        val partida = Partida(
            id = 1,
            fecha = "2025-09-24",
            jugador1 = 101,
            jugador2 = 102,
            ganadorId = 101,
            esFinalizada = true
        )
        coEvery { validarPartida(partida) } returns Result.success(Unit)
        coEvery { repository.save(partida) } returns true

        val result = useCase(partida)
        assertTrue(result.isSuccess)
        assertEquals(true, result.getOrNull())

        coVerify { validarPartida(partida) }
        coVerify { repository.save(partida) }
    }

    @Test
    fun `invoke returns failure when validation fails`() = runTest {
        val partida = Partida(
                id = 1,
        fecha = "",
        jugador1 = 0,
        jugador2 = 0,
        ganadorId = 0,
        esFinalizada = false
        )

        val exception = IllegalArgumentException("Nombre no puede estar vacío")
        coEvery { validarPartida(partida) } returns Result.failure(exception)

        val result = useCase(partida)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())

        coVerify { validarPartida(partida) }
        coVerify(exactly = 0) { repository.save(partida) }
    }
}