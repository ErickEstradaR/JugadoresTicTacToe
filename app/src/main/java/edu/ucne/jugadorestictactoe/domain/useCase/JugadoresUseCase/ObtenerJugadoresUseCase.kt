package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObtenerJugadoresUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> {
        return repository.getAllFlow()
    }
}