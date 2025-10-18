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
import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador.JugadorViewModel


@Composable
fun TicTacToeScreen(
    onDrawer: () -> Unit = {},
    viewModel: GameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TicTacToeBody(
        state = state,

        onJugadorSelected = viewModel::setJugadorX,

        startGame = viewModel::startGame,
        onCellClick = viewModel::onCellClick,
        restartGame = viewModel::restartGame,
    )
}
@Composable
private fun TicTacToeBody(
    state: GameUiState,
    onJugadorSelected: (JugadorApi) -> Unit,
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
                PlayerSelectionScreen(
                    jugadorX = state.selectedJugador,
                    onJugadorXSelected = onJugadorSelected,
                    onStartGame = { startGame() }
                )
            } else {
                GameBoard(
                    uiState = state,
                    onCellClick = { onCellClick(it) },
                    onRestartGame = { restartGame() }
                )
            }
        }
    }
}

@Composable
fun PlayerSelectionScreen(
    jugadorX: JugadorApi?,
    onJugadorXSelected: (JugadorApi) -> Unit,
    onStartGame: () -> Unit,
    viewModel: JugadorViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Elige tu jugador", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
            JugadorSelectorDesplegable(
                viewModel = viewModel,
                jugador = Player.X,
                onJugadorSeleccionado = onJugadorXSelected,
                modifier = Modifier.weight(1f)
            )


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onStartGame,
            enabled = jugadorX != null
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

@Composable
fun JugadorSelectorDesplegable(
    viewModel: JugadorViewModel = hiltViewModel(),
    jugador: Player,
    onJugadorSeleccionado: (JugadorApi) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    var selectedJugador by remember { mutableStateOf<JugadorApi?>(null) }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val jugadoresResource = state.jugadoresResource

    Box(modifier = modifier) {

        OutlinedButton(
            onClick = { expanded = true },
            enabled = jugadoresResource is Resource.Success || jugadoresResource is Resource.Error
        ) {
            val buttonText = selectedJugador?.nombres ?: "Seleccionar Jugador ${jugador.name}"
            Text(buttonText, fontSize = 18.sp)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            when (jugadoresResource) {
                is Resource.Loading -> {
                    DropdownMenuItem(
                        text = { Text("Cargando jugadores...") },
                        onClick = { /* No hacer nada */ }
                    )
                }
                is Resource.Success -> {
                    val jugadores = jugadoresResource.data

                    if (jugadores.isNullOrEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay jugadores registrados") },
                            onClick = { /* No seleccionar */ }
                        )
                    } else {
                        jugadores.forEach { jugadorApi ->
                            DropdownMenuItem(
                                text = { Text(jugadorApi.nombres ?: "Jugador sin nombre") },
                                onClick = {
                                    selectedJugador = jugadorApi
                                    onJugadorSeleccionado(jugadorApi)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    DropdownMenuItem(
                        text = { Text("Error: ${jugadoresResource.message ?: "Desconocido"}") },
                        onClick = {
                            viewModel.getJugadores()
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


