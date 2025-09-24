package edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase

data class LogrosUseCases (
    val eliminarLogro : EliminarLogroUseCase,
    val guardarLogro : GuardarLogroUseCase,
    val obtenerLogro : ObtenerLogroUseCase,
    val obtenerLogros : ObtenerLogrosUseCase,
)

