package edu.ucne.jugadorestictactoe.presentation.Tecnico


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun TecnicoScreen(
    tecnicoId: Int?,
    viewModel: TecnicoViewModel = hiltViewModel(),
    goback: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        tecnicoId?.takeIf { it > 0 }?.let {
            viewModel.findTecnico(it)
        }
    }

    TecnicoBodyScreen(
        uiState = uiState,
        onAction = viewModel::onEvent,
        goback = goback,
        saveTecnico = {
            scope.launch {
                if (viewModel.saveTecnico()) goback()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnicoBodyScreen(
    uiState: TecnicoUiState,
    onAction: (TecnicoEvent) -> Unit,
    goback: () -> Unit,
    saveTecnico: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.tecnicoId != null && uiState.tecnicoId != 0)
                            "Editar Técnico"
                        else
                            "Nuevo Técnico"
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
                value = uiState.tecnicoId?.toString() ?: "0",
                onValueChange = {},
                label = { Text("Id:") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { onAction(TecnicoEvent.nombreChange(it)) },
                label = { Text("Nombre:") },
                modifier = Modifier.fillMaxWidth(),
                isError = !uiState.errorMessage.isNullOrEmpty()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.salarioHora?.toString() ?: "",
                onValueChange = {
                    val valor = it.toDoubleOrNull() ?: 0.0
                    onAction(TecnicoEvent.salarioHoraChange(valor))
                },
                label = { Text("Salario Hora:") },
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
                OutlinedButton(onClick = { onAction(TecnicoEvent.new) }) {
                    Text("Limpiar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Refresh, "Limpiar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(onClick = { scope.launch { saveTecnico() } }) {
                    Text("Guardar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Edit, "Guardar")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTecnicoBodyScreen() {
    TecnicoBodyScreen(
        uiState = TecnicoUiState(
            tecnicoId = 1,
            nombre = "Juan Pérez",
            salarioHora = 15.5,
            errorMessage = null
        ),
        onAction = {},
        goback = {},
        saveTecnico = {}
    )
}
