package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases

import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.repository.JugadorApiRepository


class ObtenerJugadorApiUseCase (
    private val repository: JugadorApiRepository
) {
    suspend operator fun invoke(id: Int): JugadorApi? {
        return repository.getJugador(id)
    }
}
