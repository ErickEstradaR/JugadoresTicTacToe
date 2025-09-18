package edu.ucne.jugadorestictactoe.presentation.Partida

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun PartidaScreen(
    partidaId: Int?,
    viewModel: PartidaViewModel = hiltViewModel(),
    goback: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        partidaId?.takeIf { it > 0 }?.let {
            viewModel.findPartida(it)
        }
    }

    PartidaBodyScreen(
        uiState = uiState,
        onAction = viewModel::onEvent,
        goback = goback,
        savePartida = {
            scope.launch {
                if (viewModel.savePartida()) goback()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidaBodyScreen(
    uiState: PartidaUiState,
    onAction: (PartidaEvent) -> Unit,
    goback: () -> Unit,
    savePartida: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.id != null && uiState.id != 0)
                            "Editar Partida"
                        else
                            "Nueva Partida"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = goback) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = uiState.jugador1.toString(),
                onValueChange = {
                    val id = it.toIntOrNull() ?: 0
                    onAction(PartidaEvent.jugador1Change(id))
                },
                label = { Text("Id Jugador 1") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.jugador2.toString(),
                onValueChange = {
                    val id = it.toIntOrNull() ?: 0
                    onAction(PartidaEvent.jugador2Change(id))
                },
                label = { Text("Id Jugador 2") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.ganadorId?.toString() ?: "",
                onValueChange = {
                    val id = it.toIntOrNull()
                    onAction(PartidaEvent.ganadorChange(id))
                },
                label = { Text("Id Ganador") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty(),
                readOnly = !uiState.esFinalizada
            )
            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.esFinalizada,
                    onCheckedChange = { onAction(PartidaEvent.esFinalizadaChange(it)) }
                )
                Text("Partida finalizada")
            }

            uiState.errorMessage?.takeIf { it.isNotEmpty() }?.let {
                Spacer(Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(onClick = { onAction(PartidaEvent.new) }) {
                    Text("Limpiar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Refresh, "Limpiar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(onClick = { scope.launch { savePartida() } }) {
                    Text("Guardar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Edit, "Guardar")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PartidaBodyScreenPreview() {
    PartidaBodyScreen(
        uiState = PartidaUiState(
            id = 1,
            fecha = "2025-09-14",
            jugador1 = 101,
            jugador2 = 102,
            ganadorId = 101,
            esFinalizada = true,
            errorMessage = ""
        ),
        onAction = {},
        goback = {},
        savePartida = {}
    )
}

