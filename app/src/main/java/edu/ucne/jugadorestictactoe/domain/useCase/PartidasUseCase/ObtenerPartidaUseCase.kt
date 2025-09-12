package edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase

import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository

class ObtenerPartidaUseCase (
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(id: Int): Partida? {
        return repository.find(id)
    }
}