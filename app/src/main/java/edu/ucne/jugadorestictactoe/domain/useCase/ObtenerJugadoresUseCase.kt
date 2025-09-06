package edu.ucne.jugadorestictactoe.domain.useCase

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow

class ObtenerJugadoresUseCase(
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> {
        return repository.getAllFlow() 
    }
}