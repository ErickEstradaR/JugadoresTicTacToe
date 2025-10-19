package edu.ucne.jugadorestictactoe.presentation.Tecnico


sealed interface TecnicoEvent {
    data class tecnicoIdChange(val tecnicoId: Int?) : TecnicoEvent
    data class nombreChange(val nombre: String) : TecnicoEvent
    data class salarioHoraChange(val salarioHora: Double) : TecnicoEvent

    data object save : TecnicoEvent
    data object delete : TecnicoEvent
    data object new : TecnicoEvent
}
