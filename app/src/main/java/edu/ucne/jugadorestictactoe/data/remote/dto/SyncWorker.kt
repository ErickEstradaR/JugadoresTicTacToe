package edu.ucne.jugadorestictactoe.data.remote.dto

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val taskRepository: JugadorRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val result = taskRepository.postPendingJugadores()
        Log.d("SyncWorker", "postPendingJugadores result = $result")

        return when (taskRepository.postPendingJugadores()) {
            is Resource.Success -> Result.success()
            is Resource.Error -> Result.retry()
            else -> Result.failure()
        }
    }
}