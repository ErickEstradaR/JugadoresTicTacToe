package edu.ucne.jugadorestictactoe.data.remote.dto.jugador

data class JugadorResponse(
    val jugadorId : Int? = null,
    val nombres: String,
    val email: String,
)