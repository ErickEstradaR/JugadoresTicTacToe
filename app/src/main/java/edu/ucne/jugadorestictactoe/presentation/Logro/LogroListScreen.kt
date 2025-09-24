package edu.ucne.jugadorestictactoe.presentation.Logro

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.jugadorestictactoe.domain.model.Logro


@Composable
fun LogrolistScreen(
    viewModel: LogroViewModel = hiltViewModel(),
    goToLogro: (Int) -> Unit,
    createLogro: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.takeIf { it.isNotEmpty() }?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }

    val onDelete: (Logro) -> Unit = { logro ->
        viewModel.onEvent(LogroEvent.logroChange(logro.id ?: 0))
        viewModel.onEvent(LogroEvent.delete)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = createLogro) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar logro")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(state.logros) { logro ->
                LogroCardItem(
                    logro = logro,
                    goToLogro = { goToLogro(logro.id ?: 0) },
                    deleteLogro = { onDelete(logro) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun LogroCardItem(
    logro: Logro,
    goToLogro: () -> Unit,
    deleteLogro: () -> Unit
){
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
                Text(text = "Id: ${logro.id}", fontWeight = FontWeight.Bold)
                Text(text = logro.nombre, fontSize = 14.sp)
                Text(text = "${logro.descripcion}")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = goToLogro) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = deleteLogro) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogroCardItem() {
    LogroCardItem(
        logro = Logro(
            id = 1,
            nombre = "Logro de prueba",
            descripcion = "Este es un logro de ejemplo"
        ),
        goToLogro = {},
        deleteLogro = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLogroList() {
    val fakeLogros = listOf(
        Logro(1, "Primer Logro", "Descripción corta del logro"),
        Logro(2, "Segundo Logro", "Otro logro de prueba"),
        Logro(3, "Tercer Logro", "Este es un logro más largo para probar el diseño")
    )

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(fakeLogros) { logro ->
            LogroCardItem(
                logro = logro,
                goToLogro = {},
                deleteLogro = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}




