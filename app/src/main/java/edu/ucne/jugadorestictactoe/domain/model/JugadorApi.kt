package edu.ucne.jugadorestictactoe.domain.model

data class JugadorApi (
    val jugadorId: Int? = null,
    val nombres: String,
    val email: String,
    val fechaCreacion: String?,
    val victorias: Int?,
    val derrotas: Int?,
    val empates: Int?,

    val partidasComoJugador1: List<PartidaApi>?,
    val partidasComoJugador2: List<PartidaApi>?,
    val partidasGanadas: List<PartidaApi>?,


    val movimientos: List<Movimiento>?
)