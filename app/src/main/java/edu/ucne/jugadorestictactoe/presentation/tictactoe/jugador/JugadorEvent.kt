package edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador

sealed interface JugadorEvent {
    data class jugadorIdChange(val jugadorId: Int?) : JugadorEvent
    data class nombreChange(val nombre: String) : JugadorEvent
    data class emailChange(val email: String) : JugadorEvent

    data object save : JugadorEvent
    data object new : JugadorEvent
}