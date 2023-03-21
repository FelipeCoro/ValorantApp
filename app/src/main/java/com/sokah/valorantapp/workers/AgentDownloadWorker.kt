package com.sokah.valorantapp.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sokah.valorantapp.data.repository.IAgentRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class AgentDownloadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerparams: WorkerParameters,
    val repository: IAgentRepository
) : CoroutineWorker(appContext, workerparams) {


    override suspend fun doWork(): Result {

        val result = repository.getAllAgents()


        return if (result.isSuccess) {
            Log.e("TAG", "mega rip")
            Result.success()

        } else {
            Result.failure()
        }


    }
}