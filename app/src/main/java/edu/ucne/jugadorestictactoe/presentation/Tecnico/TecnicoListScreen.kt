package edu.ucne.jugadorestictactoe.presentation.Tecnico
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.jugadorestictactoe.domain.model.Tecnico

@Composable
fun TecnicoListScreen(
    viewModel: TecnicoViewModel = hiltViewModel(),
    goToTecnico: (Int) -> Unit,
    createTecnico: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.takeIf { it.isNotEmpty() }?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }

    val onDelete: (Tecnico) -> Unit = { tecnico ->
        viewModel.onEvent(TecnicoEvent.tecnicoIdChange(tecnico.tecnicoId))
        viewModel.onEvent(TecnicoEvent.delete)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = createTecnico) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar técnico")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(state.tecnicos) { tecnico ->
                TecnicoCardItem(
                    tecnico = tecnico,
                    goToTecnico = {
                        tecnico.tecnicoId?.let { id ->
                            goToTecnico(id)
                        }
                    },
                    deleteTecnico = { onDelete(tecnico) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TecnicoCardItem(
    tecnico: Tecnico,
    goToTecnico: () -> Unit,
    deleteTecnico: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(text = "Id: ${tecnico.tecnicoId}", fontWeight = FontWeight.Bold)
                Text(text = tecnico.nombres, fontSize = 14.sp)
                Text(text = "Salario Hora: ${tecnico.salarioHora}")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = goToTecnico) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = deleteTecnico) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTecnicoCardItem() {
    TecnicoCardItem(
        tecnico = Tecnico(
            tecnicoId = 1,
            nombres = "Juan Pérez",
            salarioHora = 15.5
        ),
        goToTecnico = {},
        deleteTecnico = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewTecnicoList() {
    val fakeTecnicos = listOf(
        Tecnico(1, "Juan Pérez", 15.5),
        Tecnico(2, "Ana Gómez", 18.0),
        Tecnico(3, "Carlos Díaz", 20.0)
    )

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(fakeTecnicos) { tecnico ->
            TecnicoCardItem(
                tecnico = tecnico,
                goToTecnico = {},
                deleteTecnico = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
