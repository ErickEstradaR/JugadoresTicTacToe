package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository

class ValidarJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Result<Unit> {
        // Validar campos vacíos o negativos
        if (jugador.nombre.isBlank() || jugador.partidas < 0) {
            return Result.failure(Exception("Nombre vacío o partidas negativas"))
        }

        // Verificar duplicados (excepto si se está editando el mismo jugador)
        val jugadores = repository.getAll()
        val nombreRepetido = jugadores.any {
            it.nombre.equals(jugador.nombre, ignoreCase = true) &&
                    it.id != jugador.id
        }
        if (nombreRepetido) {
            return Result.failure(Exception("Ya existe un jugador con ese nombre"))
        }

        return Result.success(Unit)
    }
}