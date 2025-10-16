package edu.ucne.jugadorestictactoe.data.remote.dto.partida

data class MovimientoDto (
    val PartidaId : Int,
    val Jugador : String = "X",
    val PosicionFila : Int,
    val PosicionColumna : Int
)




