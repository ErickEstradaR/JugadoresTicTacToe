package edu.ucne.jugadorestictactoe.data.remote.dto.partida

data class MovimientoDto(
    val MovimientoId: Int?,
    val PartidaId: Int?,
    val JugadorId: Int?,
    val PosicionFila: Int?,
    val PosicionColumna: Int?,
    val FechaMovimiento: String?,
    val Partida: String?,
    val Jugador: String?
)




