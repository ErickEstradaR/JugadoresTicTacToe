package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.MovimientoUseCases

class MovimientoUseCases(
    val crearMovimiento: RegistrarMovimientoUseCase,
    val obtenerMovimientosDePartida: ObtenerMovimientosDePartidaUseCase
)