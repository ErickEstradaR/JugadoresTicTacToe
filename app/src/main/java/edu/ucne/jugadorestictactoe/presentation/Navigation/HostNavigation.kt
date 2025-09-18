package edu.ucne.jugadorestictactoe.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorListScreen
import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorScreen
import edu.ucne.jugadorestictactoe.presentation.Partida.PartidaListScreen
import edu.ucne.jugadorestictactoe.presentation.Partida.PartidaScreen
import edu.ucne.jugadorestictactoe.presentation.tictactoe.TicTacToeScreen

@Composable
fun HostNavigation(
    navHostController: NavHostController,
    modifier : Modifier
    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.List,
        modifier = modifier
    ) {
        composable<Screen.List> {
            JugadorListScreen(
                goToJugadores = { id ->
                    navHostController.navigate(Screen.Jugador(id))
                },
                createJugador = {
                    navHostController.navigate(Screen.Jugador(null))
                }
            )
        }

        composable<Screen.PartidaList> {
            PartidaListScreen(
                goToPartidas = {id ->
                    navHostController.navigate(Screen.Partida(id))
                },
                createPartida = {
                    navHostController.navigate(Screen.Partida(null))
                }
            )
        }

        composable<Screen.Jugador> { backStack ->
            val jugadorId = backStack.toRoute<Screen.Jugador>().Id
            JugadorScreen(
                jugadorId = jugadorId ?: 0,
                goback = {navHostController.popBackStack()}
            )
        }

        composable<Screen.Partida> { backStack ->
            val partidaId = backStack.toRoute<Screen.Partida>().Id
            PartidaScreen(
                partidaId = partidaId ?: 0 ,
                goback = {navHostController.popBackStack()}
            )
        }

        composable<Screen.GameScreen> {
            TicTacToeScreen(
            )
        }
    }
}
//capa de datos: repositorio
//capa de dominio: use cases
//capa de presentacion: view models y pantallas
