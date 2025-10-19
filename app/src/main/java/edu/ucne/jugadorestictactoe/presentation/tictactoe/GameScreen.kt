package edu.ucne.jugadorestictactoe.presentation.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.jugadorestictactoe.domain.model.Jugador


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeScreen(
    onDrawer: () -> Unit = {},
    viewModel: GameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectingFor by remember { mutableStateOf<Player?>(null) }

    if (state.showPlayerList && selectingFor != null) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { viewModel.hidePlayerList() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Selecciona jugador para ${selectingFor?.symbol}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.height(16.dp))

                state.jugadores.forEach { jugador ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (selectingFor == Player.X) {
                                    viewModel.selectPlayerForX(jugador)
                                } else {
                                    viewModel.selectPlayerForO(jugador)
                                }
                                viewModel.hidePlayerList()
                                selectingFor = null
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(jugador.nombre, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Composable
private fun TicTacToeBody(
    state: GameUiState,
    onSelectX: () -> Unit,
    onSelectO: () -> Unit,
    startGame: () -> Unit,
    onCellClick: (Int) -> Unit,
    restartGame: () -> Unit,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!state.gameStarted) {
                Text("Elige tus jugadores", fontSize = 28.sp, fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(24.dp))

                Button(onClick = onSelectX) {
                    Text("Seleccionar Jugador para X")
                }

                Spacer(Modifier.height(12.dp))

                Button(onClick = onSelectO) {
                    Text("Seleccionar Jugador para O")
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = startGame,
                    enabled = state.jugadorX != null && state.jugadorO != null
                ) {
                    Text("Iniciar Partida", fontSize = 18.sp)
                }
            } else {
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
fun PlayerSelectionScreen(
    jugadores: List<Jugador>,
    jugadorX: Jugador?,
    jugadorO: Jugador?,
    onSelectPlayerX: (Jugador) -> Unit,
    onSelectPlayerO: (Jugador) -> Unit,
    onStartGame: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Elige tus jugadores", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Selecciona jugador para X", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        jugadores.forEach { jugador ->
            Button(
                onClick = { onSelectPlayerX(jugador) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(jugador.nombre)
            }
        }

        Spacer(Modifier.height(24.dp))

        Text("Selecciona jugador para O", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        jugadores.forEach { jugador ->
            Button(
                onClick = { onSelectPlayerO(jugador) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(jugador.nombre)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onStartGame,
            enabled = jugadorX != null && jugadorO != null
        ) {
            Text("Iniciar Partida", fontSize = 18.sp)
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
        uiState.winner != null -> {
            val ganador = if (uiState.winner == Player.X) uiState.jugadorX else uiState.jugadorO
            "🏆 ¡Ganador: ${ganador?.nombre}!"
        }
        uiState.isDraw -> "🤝 ¡Es un empate!"
        else -> {
            val turno = if (uiState.currentPlayer == Player.X) uiState.jugadorX else uiState.jugadorO
            "Turno de: ${turno?.nombre}"
        }
    }
    Text(text = gameStatus, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(20.dp))
    GameBoardBody(board = uiState.board, onCellClick = onCellClick)
    Spacer(modifier = Modifier.height(20.dp))
    Button(onClick = onRestartGame) {
        Text("Reiniciar Juego", fontSize = 18.sp)
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
                        onCellClick(index)
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


