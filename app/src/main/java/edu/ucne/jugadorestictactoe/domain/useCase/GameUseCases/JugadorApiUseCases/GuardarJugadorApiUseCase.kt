package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases

import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.repository.JugadorApiRepository

class GuardarJugadorApiUseCase (private val repository: JugadorApiRepository
) {
    suspend operator fun invoke(jugador: JugadorApi) {
            repository.createJugador(jugador)

    }
}
