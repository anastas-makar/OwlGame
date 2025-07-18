package pro.progr.owlgame.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object GameWorkerSetup {
    fun scheduleWork(context: Context) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val dailyRequest = PeriodicWorkRequestBuilder<AnimalBuildingsWorker>(20, TimeUnit.MINUTES)//todo: для теста
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "CheckBuildingsWork",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            dailyRequest
        )
    }
}