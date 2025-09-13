package edu.ucne.jugadorestictactoe.presentation.Partida

import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorEvent


sealed interface PartidaEvent {
    data class partidaChange(val partidaId: Int) : PartidaEvent
    data class fechaChange(val fecha: String) : PartidaEvent
    data class jugador1Change(val jugador1: Int) : PartidaEvent
    data class jugador2Change(val jugador2: Int) : PartidaEvent
    data class ganadorChange(val ganadorId: Int?) : PartidaEvent
    data class esFinalizadaChange(val esFinalizada: Boolean) : PartidaEvent

    data object save : PartidaEvent
    data object delete : PartidaEvent
    data object new : PartidaEvent
}
