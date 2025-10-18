package edu.ucne.jugadorestictactoe.presentation.tictactoe

import edu.ucne.jugadorestictactoe.domain.model.JugadorApi

data class GameUiState(
    val partidaId: Int? = null,

    val board: List<Player?> = List(9) { null },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val isDraw: Boolean = false,
    val selectedJugador: JugadorApi? = null,

    val gameStarted: Boolean = false,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

