package edu.ucne.jugadorestictactoe.domain.logros

import app.cash.turbine.test
import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.ObtenerLogrosUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class ObtenerLogrosUseCaseTest {

    private lateinit var repository: LogroRepository
    private lateinit var useCase: ObtenerLogrosUseCase

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = ObtenerLogrosUseCase(repository)
    }

    @Test
    fun `invoke returns all logros from repository`() = runTest {
        val logros = listOf(
            Logro(id = 1, nombre = "Logro1", descripcion = "descripcion1"),
            Logro(id = 2, nombre = "Logro2",descripcion="descripcion2")
        )
        val flow: Flow<List<Logro>> = flowOf(logros)

        coEvery { repository.getAllFlow() } returns flow

        useCase().test {
            val emitted = awaitItem()
            assert(emitted == logros)
            awaitComplete()
        }
        verify { repository.getAllFlow() }
    }
}
