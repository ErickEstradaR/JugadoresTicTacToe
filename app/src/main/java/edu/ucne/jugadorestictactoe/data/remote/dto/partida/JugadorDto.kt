package edu.ucne.jugadorestictactoe.data.remote.dto.partida

data class JugadorDto(
    val jugadorId: Int? = null,
    val nombres: String,
    val email: String,
    val fechaCreacion: String?,
    val victorias: Int?,
    val derrotas: Int?,
    val empates: Int?,

    val partidasComoJugador1: List<PartidaDto>?,
    val partidasComoJugador2: List<PartidaDto>?,
    val partidasGanadas: List<PartidaDto>?,


    val movimientos: List<MovimientoDto>?
)