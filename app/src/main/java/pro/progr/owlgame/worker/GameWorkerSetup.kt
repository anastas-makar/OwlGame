package pro.progr.owlgame.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object GameWorkerSetup {
    inline fun <reified T> enqueueBackgroundSync(context: Context) where T : ListenableWorker {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val dailyRequest = PeriodicWorkRequestBuilder<T>(6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "CheckBuildingsWork",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyRequest
        )
    }

    inline fun <reified T> enqueueOneTimeGameSync(context: Context) where T : ListenableWorker {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<T>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "GameSyncWork",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

}
