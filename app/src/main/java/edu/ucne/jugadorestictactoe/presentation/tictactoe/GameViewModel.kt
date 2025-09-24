package edu.ucne.jugadorestictactoe.presentation.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ObtenerJugadoresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.toMutableList


data class GameUiState(
    val board: List<Player?> = List(9) { null },
    val currentPlayer: Player = Player.X,
    val winner: Player? = null,
    val isDraw: Boolean = false,
    val jugadorX: Jugador? = null,
    val jugadorO: Jugador? = null,
    val gameStarted: Boolean = false,
    val showPlayerList: Boolean = false,
    val jugadores: List<Jugador> = emptyList()
)

@HiltViewModel
class GameViewModel @Inject constructor(private val obtenerJugadoresUseCase: ObtenerJugadoresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GameUiState())
    val state: StateFlow<GameUiState> = _state.asStateFlow()


    fun showPlayerSelection() {
        viewModelScope.launch {
            obtenerJugadoresUseCase().collect { jugadores ->
                _state.update {
                    it.copy(
                        showPlayerList = true,
                        jugadores = jugadores
                    )
                }
            }
        }
    }

    fun selectPlayerForX(jugador: Jugador) {
        _state.update { it.copy(jugadorX = jugador) }
    }

    fun selectPlayerForO(jugador: Jugador) {
        _state.update { it.copy(jugadorO = jugador) }
    }

    fun startGame() {
        val jugadorX = _state.value.jugadorX
        val jugadorO = _state.value.jugadorO

        if (jugadorX != null && jugadorO != null) {
            _state.update { it.copy(gameStarted = true) }
        }
    }

    fun onCellClick(index: Int) {
        if (_state.value.board[index] != null || _state.value.winner != null) return

        val newBoard = _state.value.board.toMutableList()
        newBoard[index] = _state.value.currentPlayer

        val newWinner = checkWinner(newBoard)
        val isDraw = newBoard.all { it != null } && newWinner == null

        _state.update {
            it.copy(
                board = newBoard,
                currentPlayer = if (it.currentPlayer == Player.X) Player.O else Player.X,
                winner = newWinner,
                isDraw = isDraw
            )
        }
    }

    fun hidePlayerList() {
        _state.update { it.copy(showPlayerList = false) }
    }

    fun restartGame() {
        _state.value = GameUiState()
    }

    private fun checkWinner(board: List<Player?>): Player? {
        val winningLines = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )

        for (line in winningLines) {
            val (a, b, c) = line
            if (board[a] != null && board[a] == board[b] && board[a] == board[c]) {
                return board[a]
            }
        }
        return null
    }
}


enum class Player(val symbol: String) {
    X("X"),
    O("O")
}
