package pro.progr.owlgame.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import pro.progr.owlgame.data.db.OwlGameDatabase
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class AnimalBuildingsWorker(
    context: Context,
    workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.wtf("WORKER IS WORKING", LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))

        val db = OwlGameDatabase.getDatabase(context = applicationContext)

        val animalDao = db.animalDao()
        Log.wtf("AnimalDao count searching: ", animalDao.countSearching().toString())

        return Result.success()
    }
}