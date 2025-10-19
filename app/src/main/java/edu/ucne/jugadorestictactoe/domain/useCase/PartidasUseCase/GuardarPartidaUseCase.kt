package edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase

import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository

class GuardarPartidaUseCase(
    private val repository: PartidaRepository,
    private val validarPartidaUseCase: ValidarPartidaUseCase
) {
    suspend operator fun invoke(partida: Partida): Result<Boolean> {
        val validacion = validarPartidaUseCase(partida)
        if (validacion.isFailure)
            return Result.failure(validacion.exceptionOrNull()!!)

        val result = repository.save(partida)
        return Result.success(result)
    }
}

