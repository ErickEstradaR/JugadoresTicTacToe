package edu.ucne.jugadorestictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.jugadorestictactoe.presentation.Navigation.HostNavigation
import edu.ucne.jugadorestictactoe.presentation.Navigation.MainScreen
import edu.ucne.jugadorestictactoe.ui.theme.JugadoresTicTacToeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JugadoresTicTacToeTheme {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}

