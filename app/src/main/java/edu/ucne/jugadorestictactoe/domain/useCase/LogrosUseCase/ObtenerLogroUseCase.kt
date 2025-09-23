package edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase

import edu.ucne.jugadorestictactoe.domain.model.Logro

import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository

class ObtenerLogroUseCase (
    private val repository: LogroRepository
) {
    suspend operator fun invoke(id: Int): Logro? {
        return repository.find(id)
    }
}