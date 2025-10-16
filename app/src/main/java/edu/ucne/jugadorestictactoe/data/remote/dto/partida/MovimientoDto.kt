package edu.ucne.jugadorestictactoe.data.remote.dto.partida

import com.squareup.moshi.Json

data class MovimientoDto (
val PartidaId : Int,
val Jugador : String = "X",
val PosicionFila : Int,
val PosicionColumna : Int
)




