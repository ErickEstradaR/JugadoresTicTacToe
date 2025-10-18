package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases

data class JugadoresUseCase (
    val obtenerJugadores: ObtenerJugadoresApiUseCase,
    val obtenerJugador: ObtenerJugadorApiUseCase,
    val guardarJugador: GuardarJugadorApiUseCase,
)