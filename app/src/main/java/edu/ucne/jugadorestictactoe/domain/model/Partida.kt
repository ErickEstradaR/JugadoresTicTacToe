package edu.ucne.jugadorestictactoe.domain.model

data class Partida(
    val id: Int? = null,
    val fecha: String,
    val jugador1: Int,
    val jugador2: Int,
    val ganadorId: Int?,
    val esFinalizada: Boolean
)