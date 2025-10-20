package edu.ucne.jugadorestictactoe.presentation.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.MovimientoUseCases.MovimientoUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.PartidaUseCases.PartidaApiUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach


@HiltViewModel
class GameViewModel @Inject constructor(
    private val useCases: PartidaApiUseCases,
    private val movimientos : MovimientoUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(GameUiState())
    val state: StateFlow<GameUiState> = _state.asStateFlow()


    private val JUGADOR_X_ID = 1
    private val JUGADOR_O_ID = 2

    private var currentPartidaId: Int? = null

    fun loadPartida(partidaId: Int) {
        if (_state.value.isLoading) return
        currentPartidaId = partidaId
        _state.update { it.copy(isLoading = true, loadingError = null) }

        viewModelScope.launch {
            try {

                val movimientos = movimientos.obtenerMovimientosDePartida(partidaId)

                val initialBoard = List<Player?>(9) { null }.toMutableList()

                movimientos.forEach { movimiento ->
                    val fila = movimiento.posicionFila
                    val columna = movimiento.posicionColumna
                    val jugador = mapStringToPlayer(movimiento.jugador)

                    if (fila != null && columna != null && jugador != null) {
                        val index = toBoardIndex(fila, columna)
                        initialBoard[index] = jugador
                    }
                }

                val nextPlayer = if (movimientos.size % 2 == 0) Player.X else Player.O

                val newWinner = checkWinner(initialBoard)
                val isDraw = initialBoard.all { it != null } && newWinner == null

                _state.update {
                    it.copy(
                        board = initialBoard.toList(),
                        currentPlayer = nextPlayer,
                        winner = newWinner,
                        isDraw = isDraw,
                        isLoading = false,
                        gameStarted = true
                    )
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        loadingError = "Fallo al cargar la partida: ${e.message}"
                    )
                }
            }
        }
    }
    fun onCellClick(index: Int) {
        if (_state.value.board[index] != null || _state.value.winner != null || _state.value.isDraw) {
            return
        }
        registerMove(index, _state.value.currentPlayer)
        val newBoard = _state.value.board.toMutableList()
        newBoard[index] = _state.value.currentPlayer

        val newWinner = checkWinner(newBoard)
        val isDraw = newBoard.all { it != null } && newWinner == null

        _state.update {
            it.copy(
                board = newBoard.toList(),
                currentPlayer = if (newWinner == null && !isDraw) it.currentPlayer.opponent else it.currentPlayer,
                winner = newWinner,
                isDraw = isDraw
            )
        }
    }

    private fun registerMove(index: Int, player: Player) {
        val partidaId = currentPartidaId
        if (partidaId == null) return

        val fila = index / 3
        val columna = index % 3

        val movimiento = Movimiento(
            partidaId = partidaId,
            jugador = player.symbol,
            posicionFila = fila,
            posicionColumna = columna
        )

        viewModelScope.launch {
            try {
                movimientos.crearMovimiento(movimiento)
            } catch (e: Exception) {
                println("Error al registrar movimiento: ${e.message}")
            }
        }
    }


    fun restartGame() {
        _state.value = GameUiState()
    }

    fun iniciarPartida() {
        if (_state.value.isPosting) return

        _state.update { it.copy(isPosting = true, postError = null) }

        val partida = PartidaApi(
            jugador1Id = JUGADOR_X_ID,
            jugador2Id = JUGADOR_O_ID
        )

        viewModelScope.launch {
            try {
                val nuevaPartida = useCases.crearPartidaApi(partida)
                _state.update { currentState ->
                    currentState.copy(
                        isPosting = false,
                        gameStarted = true,
                        board = List(9) { null },
                        currentPlayer = Player.X
                    )
                }
            } catch (e: Exception) {
                _state.update { currentState ->
                    currentState.copy(
                        isPosting = false,
                        postError = "Error al iniciar partida: ${e.message ?: "Error desconocido"}"
                    )
                }
            }
        }
    }

    private fun checkWinner(board: List<Player?>): Player? {
        val winningLines = listOf(
            // Horizontales
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            // Verticales
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            // Diagonales
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
    O("O");

    val opponent: Player
        get() = when (this) {
            X -> O
            O -> X
        }
}

private fun mapStringToPlayer(playerString: String): Player? {
    return when (playerString.uppercase()) {
        "X" -> Player.X
        "O" -> Player.O
        else -> null
    }
}

private fun toBoardIndex(fila: Int, columna: Int): Int = fila * 3 + columna