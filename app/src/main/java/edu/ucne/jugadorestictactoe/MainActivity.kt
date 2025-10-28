package edu.ucne.jugadorestictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.jugadorestictactoe.data.remote.dto.SyncWorker
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
                MainScreen(navController = navController)
            }
        }
        startSyncWorker()
    }
    private fun startSyncWorker() {
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(syncRequest)
    }
}
