package pro.progr.owlgame.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import pro.progr.owlgame.domain.SearchAnimalsUseCase
import javax.inject.Inject

class GameWorkerFactory @Inject constructor(
    private val searchAnimalsUseCase: SearchAnimalsUseCase
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            AnimalBuildingsWorker::class.java.name -> AnimalBuildingsWorker(
                appContext,
                workerParameters,
                searchAnimalsUseCase
            )
            else -> null
        }
    }
}