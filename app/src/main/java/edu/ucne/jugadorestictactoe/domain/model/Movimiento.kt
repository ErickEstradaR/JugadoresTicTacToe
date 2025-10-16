package edu.ucne.jugadorestictactoe.domain.model

data class Movimiento (
    val MovimientoId: Int?,
    val PartidaId: Int?,
    val JugadorId: Int?,
    val PosicionFila: Int?,
    val PosicionColumna: Int?,
    val FechaMovimiento: String?,
    val Partida: String?,
    val Jugador: String?
)