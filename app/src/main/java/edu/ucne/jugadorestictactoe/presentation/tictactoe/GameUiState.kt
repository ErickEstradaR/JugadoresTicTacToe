package edu.ucne.jugadorestictactoe.presentation.tictactoe


data class GameUiState(
    val board: List<Player?> = List(9) { null },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val isDraw: Boolean = false,
    val gameStarted: Boolean = false,
    val isPosting: Boolean = false,
    val postError: String? = null,
    val isLoading : Boolean = false,
    val loadingError: String? = null,
)

