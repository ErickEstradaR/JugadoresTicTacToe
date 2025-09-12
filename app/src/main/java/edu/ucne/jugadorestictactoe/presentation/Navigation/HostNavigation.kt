package edu.ucne.jugadorestictactoe.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.navigation.compose.composable
import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorListScreen
import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorScreen



@Composable
fun HostNavigation(
    navHostController: NavHostController,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.List
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

        composable<Screen.Jugador> { backStack ->
            val jugadorId = backStack.toRoute<Screen.Jugador>().Id
            JugadorScreen(
                jugadorId = jugadorId ?: 0,
                goback = {navHostController.popBackStack()}
            )

        }
    }
}

