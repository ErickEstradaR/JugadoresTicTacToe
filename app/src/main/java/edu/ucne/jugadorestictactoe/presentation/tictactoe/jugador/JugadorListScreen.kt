package edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi



@Composable
fun JugadorApiListScreen(
    viewModel: JugadorViewModel = hiltViewModel(),
    goToJugador: (Int) -> Unit,
    createJugador: () -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.takeIf { it.isNotEmpty() }?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = createJugador) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Jugador")
            }
        }
    ) { paddingValues ->

        when (val resource = state.jugadoresResource) {
            is Resource.Loading -> {
                LoadingIndicator(modifier = Modifier.padding(paddingValues))
            }

            is Resource.Error -> {

                ErrorContent(
                    message = resource.message ?: "Error desconocido al cargar la lista.",
                    onRetry = viewModel::getJugadores,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is Resource.Success -> {

                resource.data?.let { jugadores ->
                    if (jugadores.isEmpty()) {
                        EmptyContent(modifier = Modifier.padding(paddingValues))
                    } else {
                        JugadoresList(
                            jugadores = jugadores,
                            paddingValues = paddingValues,
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun JugadoresList(
    jugadores: List<JugadorApi>,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        items(jugadores) { jugador ->
            JugadorCardItem(
                jugador = jugador,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
}

@Composable
fun ErrorContent(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {

}

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {

}


@Composable
fun JugadorCardItem(
    jugador: JugadorApi,
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
                Text(text = "Nombre: ${jugador.nombres}", fontWeight  = FontWeight.Bold)
                Text(text = "Email: ${jugador.email}", fontWeight  = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))

            }
        }
    }
