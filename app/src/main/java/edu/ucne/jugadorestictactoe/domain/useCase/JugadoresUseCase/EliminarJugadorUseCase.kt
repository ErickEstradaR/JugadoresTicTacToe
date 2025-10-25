package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import javax.inject.Inject

class EliminarJugadorUseCase @Inject constructor(
    private val repo: JugadorRepository
    ) {
    suspend operator fun invoke(id: String): Resource<Unit> = repo.delete(id)
}