package edu.ucne.jugadorestictactoe.data.remote.dto.partida

data class PartidaDto (
    val PartidaId: Int?,
    val Jugador1Id: Int?,
    val Jugador2Id: Int?,

    val EstadoPartida: String?,
    val GanadorId: Int?,
    val TurnoJugadorId: Int?,
    val EstadoTablero: String?,

    val FechaInicio: String?,
    val FechaFin: String?,

    val Jugador1: String?,
    val Jugador2: String?,
    val Ganador: String?,
    val TurnoJugador: String?,

    val Movimientos: List<MovimientoDto>?
)
