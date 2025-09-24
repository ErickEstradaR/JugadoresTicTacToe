package edu.ucne.jugadorestictactoe.domain.logros

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ObtenerJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.ObtenerLogroUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.ObtenerLogrosUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class ObtenerLogroUseCaseTest {

    private lateinit var repository: LogroRepository
    private lateinit var useCase: ObtenerLogroUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = ObtenerLogroUseCase(repository)
    }

    @Test
    fun `invoke returns logro when found`() = runTest {
        val logro = Logro(id = 1, nombre = "logro", descripcion = "logro importante")
        coEvery { repository.find(1) } returns logro

        val result = useCase(1)

        assertEquals(logro, result)
        coVerify { repository.find(1) }
    }

    @Test
    fun `invoke returns null when logro not found`() = runTest {
        coEvery { repository.find(2) } returns null

        val result = useCase(2)

        assertNull(result)
        coVerify { repository.find(2) }
    }
}
