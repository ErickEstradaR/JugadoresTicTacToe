package edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun JugadorApiScreen(
    jugadorId: Int?,
    viewModel: JugadorViewModel = hiltViewModel(),
    goback: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        jugadorId?.takeIf { it > 0 }?.let {
            viewModel.findJugador(it)
        }
    }

    JugadorBodyScreen(
        uiState = uiState,
        onAction = viewModel::onEvent,
        goback = goback,
        saveJugador = {
            scope.launch {
                if (viewModel.saveJugador()) goback()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadorBodyScreen(
    uiState: JugadorUiState,
    onAction: (JugadorEvent) -> Unit,
    goback: () -> Unit,
    saveJugador: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.JugadorId != null && uiState.JugadorId != 0)
                            "Editar Jugador"
                        else
                            "Nuevo Jugador"
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
                value = uiState.JugadorId?.toString() ?: "0",
                onValueChange = {},
                label = { Text("Id:") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.Nombres,
                onValueChange = { onAction(JugadorEvent.nombreChange(it)) },
                label = { Text("Nombre:") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.Email,
                onValueChange = { onAction(JugadorEvent.emailChange(it)) },
                label = { Text("eMail:") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )

            uiState.errorMessage?.takeIf { it.isNotEmpty() }?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(onClick = { onAction(JugadorEvent.new) }) {
                    Text("Limpiar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Refresh, "Limpiar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(onClick = { scope.launch { saveJugador() } }) {
                    Text("Guardar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Edit, "Guardar")
                }
            }
        }
    }
}

