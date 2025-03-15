package pro.progr.owlgame.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import pro.progr.owlgame.domain.SearchAnimalsUseCase
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class AnimalBuildingsWorker(
    context: Context,
    workerParams: WorkerParameters,
    val searchAnimalsUseCase: SearchAnimalsUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.wtf("WORKER IS WORKING", LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))

        searchAnimalsUseCase()

        return Result.success()
    }
}