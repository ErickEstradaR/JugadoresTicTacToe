package edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ValidarJugadorUseCase

class GuardarPartidaUseCase (
    private val repository: PartidaRepository,
) {
    suspend operator fun invoke(partida: Partida): Result<Boolean> {
        val result = repository.save(partida)
        return Result.success(result)
    }
}
