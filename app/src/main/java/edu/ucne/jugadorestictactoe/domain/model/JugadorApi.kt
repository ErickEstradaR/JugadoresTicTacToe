package edu.ucne.jugadorestictactoe.domain.model



data class JugadorApi (
    val JugadorId: Int?,
    val Nombres: String?,
    val Email: String?,


    val FechaCreacion: String?,
    val Victorias: Int?,
    val Derrotas: Int?,
    val Empates: Int?,

    val PartidasComoJugador1: List<PartidaApi>?,
    val PartidasComoJugador2: List<PartidaApi>?,
    val PartidasGanadas: List<PartidaApi>?,


    val Movimientos: List<Movimiento>?
)