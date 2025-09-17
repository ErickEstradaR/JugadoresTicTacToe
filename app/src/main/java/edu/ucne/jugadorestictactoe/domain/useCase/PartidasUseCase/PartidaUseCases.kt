package edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase

data class PartidaUseCases (
    val eliminarPartida : EliminarPartidaUseCase,
    val guardarPartida : GuardarPartidaUseCase,
    val obtenerPartida : ObtenerPartidaUseCase,
    val obtenerPartidas : ObtenerPartidasUseCase,
    val validarPartida : ValidarPartidaUseCase
)