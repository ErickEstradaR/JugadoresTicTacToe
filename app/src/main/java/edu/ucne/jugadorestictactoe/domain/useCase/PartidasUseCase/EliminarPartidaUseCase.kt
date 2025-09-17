package edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase

import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository


class EliminarPartidaUseCase (
    private val repository: PartidaRepository
    ) {
        suspend operator fun invoke(partida: Partida) {
            repository.delete(partida)
        }
    }
