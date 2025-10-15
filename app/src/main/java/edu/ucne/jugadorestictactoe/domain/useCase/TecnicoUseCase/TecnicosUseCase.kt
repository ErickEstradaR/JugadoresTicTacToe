package edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase

data class TecnicoUseCases(
    val obtenerTecnicos: ObtenerTecnicosUseCase,
    val obtenerTecnico: ObtenerTecnicoUseCase,
    val guardarTecnico: GuardarTecnicoUseCase,
    val eliminarTecnico: EliminarTecnicoUseCase
)