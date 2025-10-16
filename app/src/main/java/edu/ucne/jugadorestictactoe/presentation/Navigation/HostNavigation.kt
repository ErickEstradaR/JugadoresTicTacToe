package edu.ucne.jugadorestictactoe.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorListScreen
import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorScreen
import edu.ucne.jugadorestictactoe.presentation.Logro.LogroScreen
import edu.ucne.jugadorestictactoe.presentation.Logro.LogrolistScreen
import edu.ucne.jugadorestictactoe.presentation.Partida.PartidaListScreen
import edu.ucne.jugadorestictactoe.presentation.Partida.PartidaScreen
import edu.ucne.jugadorestictactoe.presentation.Tecnico.TecnicoListScreen
import edu.ucne.jugadorestictactoe.presentation.Tecnico.TecnicoScreen
import edu.ucne.jugadorestictactoe.presentation.tictactoe.GameViewModel
import edu.ucne.jugadorestictactoe.presentation.tictactoe.PlayerSelectionScreen
import edu.ucne.jugadorestictactoe.presentation.tictactoe.TicTacToeScreen

@Composable
fun HostNavigation(
    navHostController: NavHostController,
    modifier: Modifier
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

        composable<Screen.GameScreen> { backStack ->
            val viewModel: GameViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            PlayerSelectionScreen(
                selectedPlayer = state.playerSelection,
                onPlayerSelected = viewModel::selectPlayer,
                onStartGame = viewModel::startGame
            )
        }

        composable<Screen.Logro> { backStack ->
            val logroId = backStack.toRoute<Screen.Logro>().id
            LogroScreen(
                logroId = logroId ?: 0,
                goback = {navHostController.popBackStack()}
            )
        }

        composable<Screen.LogroList> {
            LogrolistScreen(
                goToLogro = {id ->
                    navHostController.navigate(Screen.Logro(id))
                },
                createLogro = {
                    navHostController.navigate(Screen.Logro(null))
                }
            )
        }

        composable<Screen.TecnicoList> {
            TecnicoListScreen (
                goToTecnico = {id ->
                    navHostController.navigate(Screen.Tecnico(id))
                },
                createTecnico = {
                    navHostController.navigate(Screen.Tecnico(null))
                }
            )
        }

        composable<Screen.Partida> { backStack ->
            val partidaId = backStack.toRoute<Screen.Partida>().Id
            PartidaScreen(
                partidaId = partidaId ?: 0 ,
                goback = {navHostController.popBackStack()}
            )
        }

        composable<Screen.Tecnico> { backStack ->
            val tecnicoId = backStack.toRoute<Screen.Tecnico>().id
            TecnicoScreen (
                tecnicoId = tecnicoId ?: 0 ,
                goback = {navHostController.popBackStack()}
            )
        }

        composable<Screen.GameScreen> {
            TicTacToeScreen(
            )
        }
    }
}

