package edu.ucne.jugadorestictactoe.presentation.Partida

import androidx.compose.foundation.layout.Arrangement
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
import edu.ucne.jugadorestictactoe.domain.model.Partida


@Composable
fun PartidaListScreen(
    viewModel: PartidaViewModel = hiltViewModel(),
    goToPartidas: (Int) -> Unit,
    createPartida: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.takeIf { it.isNotEmpty() }?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }

    val onDelete: (Partida) -> Unit = { partida ->
        viewModel.onEvent(PartidaEvent.partidaChange(partida.id ?: 0))
        viewModel.onEvent(PartidaEvent.delete)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = createPartida) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar partida")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(state.partidas) { partida ->
                JugadorCardItem(
                    partida = partida,
                    goToPartida = { goToPartidas(partida.id ?: 0) },
                    deletePartida = { onDelete(partida) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun JugadorCardItem(
    partida: Partida,
    goToPartida: () -> Unit,
    deletePartida: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Id Partida: ${partida.id}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${partida.fecha}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Jugador X: ${partida.jugador1}")
                    Text(text = "Jugador O: ${partida.jugador2}")
                    Text(
                        text = if (partida.esFinalizada) "Finalizada" else "No finalizada",
                        fontSize = 14.sp
                    )
                    Text(
                        text = if (partida.esFinalizada)
                            "Ganador: ${partida.ganadorId}"
                        else
                            "",
                        fontSize = 14.sp
                    )
                }

                Row {
                    IconButton(onClick = goToPartida) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = deletePartida) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                }
            }
        }
    }
}