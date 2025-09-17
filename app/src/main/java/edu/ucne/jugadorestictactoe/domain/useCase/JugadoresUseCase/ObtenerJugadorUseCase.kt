package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository

class ObtenerJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int): Jugador? {
        return repository.find(id)
    }
}