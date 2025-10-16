package edu.ucne.jugadorestictactoe.data.remote.dto.partida

data class JugadorDto(
    val JugadorId: Int?,
    val Nombres: String?,
    val Email: String?,


    val FechaCreacion: String?,
    val Victorias: Int?,
    val Derrotas: Int?,
    val Empates: Int?,

    val PartidasComoJugador1: List<PartidaDto>?,
    val PartidasComoJugador2: List<PartidaDto>?,
    val PartidasGanadas: List<PartidaDto>?,


    val Movimientos: List<MovimientoDto>?
)