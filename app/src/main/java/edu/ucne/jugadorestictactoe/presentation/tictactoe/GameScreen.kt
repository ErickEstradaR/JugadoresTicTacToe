package edu.ucne.jugadorestictactoe.presentation.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun TicTacToeScreen(
    state: GameUiState,
    onCellClick: (Int) -> Unit,
    restartGame: () -> Unit,
    viewModel: GameViewModel = hiltViewModel()
) {
    val gameState by viewModel.state.collectAsStateWithLifecycle(LocalLifecycleOwner.current)

    TicTacToeBody(
        state = gameState,
        onCellClick = viewModel::onCellClick,
        restartGame = viewModel::restartGame,
        onLoadPartida = viewModel::loadPartida,
        onStartGame = viewModel::iniciarPartida // 👈 Pass the necessary handler
    )
}

// ---

@Composable
fun IniciarJuegoScreen(
    onStartGame: () -> Unit,
    isPosting: Boolean,
    postError: String?
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Tic Tac Toe", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onStartGame,
            enabled = !isPosting
        ) {
            if (isPosting) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Iniciar Partida", fontSize = 18.sp)
            }
        }
        if (postError != null) {
            Text(
                text = "Error al iniciar: $postError",
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
private fun TicTacToeBody(
    state: GameUiState,
    onCellClick: (Int) -> Unit,
    restartGame: () -> Unit,
    onLoadPartida: (Int) -> Unit,
    onStartGame: () -> Unit
) {
    if (!state.gameStarted) {
        IniciarJuegoScreen(
            onStartGame = onStartGame,
            isPosting = state.isPosting,
            postError = state.postError
        )
    } else {
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                PartidaSearchBar(
                    isLoading = state.isLoading,
                    loadingError = state.loadingError,
                    onSearch = onLoadPartida
                )
                Spacer(modifier = Modifier.height(32.dp))
                GameBoard(
                    uiState = state,
                    onCellClick = onCellClick,
                    onRestartGame = restartGame
                )
            }
        }
    }
}

@Composable
fun GameBoard(
    uiState: GameUiState,
    onCellClick: (Int) -> Unit,
    onRestartGame: () -> Unit
) {
    val gameStatus = when {
        uiState.winner != null -> "🏆 ¡Ganador: ${uiState.winner.symbol}!"
        uiState.isDraw -> "🤝 ¡Es un empate!"
        else -> "Turno de: ${uiState.currentPlayer.symbol}"
    }
    Text(text = gameStatus, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(20.dp))
    GameBoardBody(board = uiState.board, onCellClick = onCellClick)
    Spacer(modifier = Modifier.height(20.dp))
    Button(onClick = onRestartGame) {
        Text("Reiniciar Juego", fontSize = 18.sp)
    }

    if (uiState.isPosting) {
        CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        Text("Guardando partida...", fontSize = 14.sp)
    } else if (uiState.postError != null) {
        Text("Error: ${uiState.postError}", color = Color.Red, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun GameBoardBody(board: List<Player?>, onCellClick: (Int) -> Unit) {
    Column {
        (0..2).forEach { row ->
            Row {
                (0..2).forEach { col ->
                    val index = row * 3 + col
                    BoardCell(board[index]) {
                        if (board[index] == null) {
                            onCellClick(index)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoardCell(
    player: Player?,
    onCellClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp)
            .background(Color.LightGray)
            .clickable { onCellClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player?.symbol ?: "",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = if (player == Player.X) Color.Blue else Color.Red
        )
    }
}

@Composable
fun PartidaSearchBar(
    isLoading: Boolean,
    loadingError: String?,
    onSearch: (Int) -> Unit
) {
    var partidaIdText by remember { mutableStateOf("") }
    if (!partidaIdText.isEmpty() || !isLoading) {
        Text(
            text = "ID de la Partida Actual: $partidaIdText",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = partidaIdText,
                onValueChange = { partidaIdText = it.filter { char -> char.isDigit() } },
                label = { Text("ID de Partida") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    val id = partidaIdText.toIntOrNull()
                    if (id != null && id > 0) {
                        onSearch(id)
                    }
                },
                enabled = partidaIdText.isNotEmpty() && !isLoading
            ) {
                Text("Cargar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cargando partida...")
            }
        } else if (loadingError != null) {
            Text(
                text = "Error al cargar: $loadingError",
                color = Color.Red,
                fontSize = 14.sp
            )
        }
    }
}

