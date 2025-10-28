package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

data class JugadorUseCases(
    val createJugadorLocalUseCase: CreateJugadorLocalUseCase,
    val guardarJugador: GuardarJugadorUseCase,
    val eliminarJugador: EliminarJugadorUseCase,
    val obtenerJugador: ObtenerJugadorUseCase,
    val obtenerJugadores: ObtenerJugadoresUseCase,
)