package edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase

import edu.ucne.jugadorestictactoe.domain.model.Partida

class ValidarPartidaUseCase {
    operator fun invoke(partida: Partida): Result<Unit> {
        if (partida.jugador1 == partida.jugador2) {
            return Result.failure(IllegalArgumentException("Un jugador no puede jugar contra sí mismo"))
        }
        if (partida.esFinalizada) {
            val ganador = partida.ganadorId
            if (ganador != partida.jugador1 && ganador != partida.jugador2) {
                return Result.failure(IllegalArgumentException("El ganador debe ser uno de los jugadores de la partida"))
            }
        }

        return Result.success(Unit)
    }
}
