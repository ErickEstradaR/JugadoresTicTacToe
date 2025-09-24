package edu.ucne.jugadorestictactoe.domain.logros

import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.GuardarLogroUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue


class GuardarLogroUseCaseTest {

    private lateinit var repository: LogroRepository
    private lateinit var useCase: GuardarLogroUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GuardarLogroUseCase(repository)
    }

    @Test
    fun `invoke calls repository save`() = runTest {
        val logro = Logro(id = 1, nombre = "logro", descripcion = "logro importante")

        coEvery { repository.save(logro) } returns true

        useCase(logro)

        coVerify { repository.save(logro) }
    }

    @Test
    fun `invoke throws exception when repository fails`() = runTest {
        val logro = Logro(id = 1, nombre = "logro", descripcion = "logro importante")

        val exception = IllegalArgumentException("Error al guardar")
        coEvery { repository.save(logro) } throws exception
        assertFailsWith<IllegalArgumentException> {
            useCase(logro)
        }

        coVerify { repository.save(logro) }
    }
}
