package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases

import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.repository.JugadorApiRepository
import kotlinx.coroutines.flow.Flow

class ObtenerJugadoresApiUseCase(
    private val repository: JugadorApiRepository
) {
    operator fun invoke(): Flow<List<JugadorApi>> {
        return repository.getAllFlow()
    }
}
