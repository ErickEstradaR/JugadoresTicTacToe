package edu.ucne.jugadorestictactoe

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import edu.ucne.jugadorestictactoe.di.AppModule
import edu.ucne.jugadorestictactoe.di.MyWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class JugadoresTicTacToeApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
