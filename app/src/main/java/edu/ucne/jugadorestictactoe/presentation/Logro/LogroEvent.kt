package edu.ucne.jugadorestictactoe.presentation.Logro

sealed interface LogroEvent {
    data class nombreChange(val nombre: String) : LogroEvent
    data class descripcionChange(val descripcion: String) : LogroEvent
    data class logroChange(val logroId: Int): LogroEvent
    data object save: LogroEvent
    data object delete: LogroEvent
    data object new: LogroEvent
}