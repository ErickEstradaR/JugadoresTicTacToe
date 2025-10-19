package edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase

import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository

class GuardarLogroUseCase(
    private val repository: LogroRepository
)
{
    suspend operator fun invoke(logro: Logro) {
        repository.save(logro)
    }
}
