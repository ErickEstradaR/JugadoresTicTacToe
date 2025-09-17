package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository

class EliminarJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador) {
        repository.delete(jugador)
    }
}