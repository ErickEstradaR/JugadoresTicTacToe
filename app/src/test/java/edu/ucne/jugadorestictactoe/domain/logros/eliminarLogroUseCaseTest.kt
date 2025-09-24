package edu.ucne.jugadorestictactoe.domain.logros

import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.EliminarLogroUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class EliminarJugadorUseCaseTest {

    private lateinit var repository: LogroRepository
    private lateinit var useCase: EliminarLogroUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = EliminarLogroUseCase(repository)
    }

    @Test
    fun `calls repository delete with logro`() = runTest {
        val logro = Logro(id = 1, nombre = "Logro", descripcion = "logro importante")

        coEvery { repository.delete(logro) } just runs

        useCase(logro)

        coVerify { repository.delete(logro) }
    }
}
