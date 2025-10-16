package edu.ucne.jugadorestictactoe.domain.model

data class Movimiento (
    val PartidaId : Int,
    val Jugador : String = "X",
    val PosicionFila : Int,
    val PosicionColumna : Int
)