package edu.ucne.jugadorestictactoe.presentation.Jugador

import edu.ucne.jugadorestictactoe.domain.model.Jugador

data class JugadorUiState (
    val jugadorId: Int? = null,
    val nombres: String = "",
    val partidas: Int = 0,
    val errorMessage: String? = null,
    val jugadores: List<Jugador> = emptyList()
)