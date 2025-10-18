package edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador

import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.model.PartidaApi

data class JugadorUiState(
    val JugadorId: Int? = null,
    val Nombres: String = "",
    val Email: String = "",

    val FechaCreacion: String? = null,
    val Victorias: Int? = null,
    val Derrotas: Int? = null,
    val Empates: Int? = null,

    val PartidasComoJugador1: List<PartidaApi> = emptyList(),
    val PartidasComoJugador2: List<PartidaApi> = emptyList(),
    val PartidasGanadas: List<PartidaApi> = emptyList(),

    val Movimientos: List<Movimiento> = emptyList(),
    val jugadores: List<JugadorApi> = emptyList(),
    val errorMessage: String? = null,

    val jugadoresResource: Resource<List<JugadorApi>> = Resource.Loading(),
    val actionMessage: String? = null,
    val isSaving: Boolean = false
) {
    companion object {
        fun default() = JugadorUiState()
    }
}
