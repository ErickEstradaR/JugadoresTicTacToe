package edu.ucne.jugadorestictactoe.presentation.Jugador

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.jugadorestictactoe.domain.model.Jugador

@Composable
fun JugadorListScreen(
    viewModel: JugadorViewModel = hiltViewModel(),
    goToJugadores: (Int) -> Unit,
    createJugador: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    JugadorBodyScreen(
        jugadores = state.jugadores,
        goToJugador = { id -> goToJugadores(id)},
        createJugador = createJugador,
        deleteJugador = { jugador ->
            viewModel.OnEvent(JugadorEvent.JugadorChange(jugador.id ?: 0))
            viewModel.OnEvent(JugadorEvent.delete)
        }
    )
}

@Composable
fun JugadorBodyScreen(
    jugadores: List<Jugador>,
    goToJugador: (Int) -> Unit,
    createJugador: () -> Unit,
    deleteJugador: (Jugador) -> Unit
){
    androidx.compose.material3.Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = createJugador) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar jugador")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(jugadores){ jugador ->
                JugadorCardItem(
                    jugador = jugador,
                    goToJugador = { goToJugador(jugador.id ?: 0) },
                    deleteJugador = { deleteJugador(jugador) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun JugadorCardItem(
    jugador: Jugador,
    goToJugador: () -> Unit,
    deleteJugador: (Jugador) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(text = "Id: ${jugador.id}", fontWeight = FontWeight.Bold)
                Text(text = jugador.nombre, fontSize = 14.sp)
                Text(text = "${jugador.partidas} Partidas jugadas")
            }
            IconButton(onClick = goToJugador) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = { deleteJugador(jugador) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

