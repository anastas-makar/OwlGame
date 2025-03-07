package pro.progr.owlgame.worker

import android.content.Context
import androidx.work.Configuration
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

        val dailyRequest = PeriodicWorkRequestBuilder<AnimalBuildingsWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "CheckBuildingsWork",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyRequest
        )
    }
}