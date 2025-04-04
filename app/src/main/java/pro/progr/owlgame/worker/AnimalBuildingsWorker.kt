package pro.progr.owlgame.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.web.RetrofitProvider
import pro.progr.owlgame.domain.SearchAnimalsUseCase
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import pro.progr.owlgame.BuildConfig
import pro.progr.owlgame.data.web.AnimalApiService

class AnimalBuildingsWorker(
    context: Context,
    workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.wtf("WORKER IS WORKING", LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))

        val db = OwlGameDatabase.getDatabase(context = applicationContext)

        val animalDao = db.animalDao()

        Log.wtf("AnimalDao count searching: ", animalDao.countSearching().toString())

        val animal = SearchAnimalsUseCase(
            AnimalsRepository(db.animalDao(),
                RetrofitProvider.provideRetrofit(BuildConfig.API_BASE_URL)
                    .create(AnimalApiService::class.java),
                BuildConfig.API_KEY),
            BuildingsRepository(db.buildingsDao()))()

        if (animal != null) {
            Log.wtf("Животное ищет дом", animal.name)
        }

        return Result.success()
    }
}