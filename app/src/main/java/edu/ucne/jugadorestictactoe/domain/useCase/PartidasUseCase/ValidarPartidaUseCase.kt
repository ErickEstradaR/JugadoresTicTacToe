package edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase

import edu.ucne.jugadorestictactoe.domain.model.Partida

class ValidarPartidaUseCase {
    operator fun invoke(partida: Partida): Result<Unit> {
        return if (partida.jugador1 == partida.jugador2) {
            Result.failure(IllegalArgumentException("Un jugador no puede jugar contra sí mismo"))
        } else {
            Result.success(Unit)
        }
    }
}