package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import edu.ucne.jugadorestictactoe.data.remote.dto.SyncWorker
import javax.inject.Inject

class TriggerSyncUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke() {
        val request = OneTimeWorkRequestBuilder<SyncWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }
}