package edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase

import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow

class ObtenerPartidasUseCase (  private val repository: PartidaRepository
) {
    operator fun invoke(): Flow<List<Partida>> {
        return repository.getAllFlow()
    }
}
