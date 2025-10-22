package edu.ucne.jugadorestictactoe.domain.model

data class Movimiento (
    val partidaId: Int?,
    val jugador : String,
    val posicionFila: Int?,
    val posicionColumna: Int?
)