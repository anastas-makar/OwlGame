package pro.progr.owlgame.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class AnimalBuildingsWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        TODO()
    }
}