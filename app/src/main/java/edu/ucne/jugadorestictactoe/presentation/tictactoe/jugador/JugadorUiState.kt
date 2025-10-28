package edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador

import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.model.PartidaApi

data class JugadorUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val userMessage: String? = null,
    val showCreateSheet: Boolean = false,
    val jugadorDescription: String = ""
) {
    companion object {
        fun default() = JugadorUiState()
    }
}
