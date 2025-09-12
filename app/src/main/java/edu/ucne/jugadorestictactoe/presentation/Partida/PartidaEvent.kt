package edu.ucne.jugadorestictactoe.presentation.Partida



sealed interface PartidaEvent {
    data class IdChange(val id: Int?) : PartidaEvent
    data class FechaChange(val fecha: String) : PartidaEvent
    data class Jugador1Change(val jugador1: Int) : PartidaEvent
    data class Jugador2Change(val jugador2: Int) : PartidaEvent
    data class GanadorChange(val ganadorId: Int?) : PartidaEvent
    data class EsFinalizadaChange(val esFinalizada: Boolean) : PartidaEvent

    data object Save : PartidaEvent
    data object Delete : PartidaEvent
    data object New : PartidaEvent
}
