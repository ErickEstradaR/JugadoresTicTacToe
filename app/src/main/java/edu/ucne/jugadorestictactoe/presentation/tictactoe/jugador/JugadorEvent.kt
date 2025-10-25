package edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador

import edu.ucne.jugadorestictactoe.domain.model.Jugador

sealed interface JugadorEvent {
    data class CrearJugador(val nombres: String) : JugadorEvent
    data class UpdateJugador(val jugador: Jugador) : JugadorEvent
    data class DeleteJugador(val id: String) : JugadorEvent
    object ShowCreateSheet : JugadorEvent
    object HideCreateSheet : JugadorEvent
    data class OnDescriptionChange(val description: String) : JugadorEvent
    object UserMessageShown : JugadorEvent
}
