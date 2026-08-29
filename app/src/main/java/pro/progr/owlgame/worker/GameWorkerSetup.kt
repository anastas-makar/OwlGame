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

@PublishedApi
internal const val ANIMAL_ARRIVAL_WORK_NAME = "AnimalArrivalCheckWork"

@PublishedApi
internal const val LEGACY_ANIMAL_WORK_NAME = "CheckBuildingsWork"

@PublishedApi
internal const val GAME_SYNC_WORK_NAME = "GameSyncWork"

object GameWorkerSetup {

    /**
     * Periodically checks whether a free building can attract a new animal.
     * The concrete worker is responsible for checking authorization again before network access.
     */
    inline fun <reified T> enqueuePeriodicAnimalArrivalCheck(
        context: Context
    ) where T : ListenableWorker {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<T>(6, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(LEGACY_ANIMAL_WORK_NAME)

        workManager.enqueueUniquePeriodicWork(
            ANIMAL_ARRIVAL_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun cancelPeriodicAnimalArrivalCheck(context: Context) {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(ANIMAL_ARRIVAL_WORK_NAME)
        workManager.cancelUniqueWork(LEGACY_ANIMAL_WORK_NAME)
    }

    /**
     * Runs one backup/restore pass when the app leaves the foreground.
     * KEEP prevents several lifecycle events from queuing duplicate syncs.
     */
    inline fun <reified T> enqueueOneTimeGameSync(
        context: Context
    ) where T : ListenableWorker {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<T>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            GAME_SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    fun cancelOneTimeGameSync(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(GAME_SYNC_WORK_NAME)
    }
}
