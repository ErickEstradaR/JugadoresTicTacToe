package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository


class GuardarJugadorUseCase(
    private val repository: JugadorRepository,
    private val validarJugador: ValidarJugadorUseCase
) {
    suspend operator fun invoke(jugador: Jugador): Result<Boolean> {

        val validacion = validarJugador(jugador)
        if (validacion.isFailure) return Result.failure(validacion.exceptionOrNull()!!)

        val result = repository.save(jugador)
        return Result.success(result)
    }
}
