package edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase

import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository

import kotlinx.coroutines.flow.Flow

class ObtenerLogrosUseCase (  private val repository: LogroRepository
) {
    operator fun invoke(): Flow<List<Logro>> {
        return repository.getAllFlow()
    }
}