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

        var retrofit = RetrofitProvider.provideRetrofit(BuildConfig.API_BASE_URL)

        SearchAnimalsUseCase(
            AnimalsRepository(db.animalDao(), retrofit.create(AnimalApiService::class.java)),
            BuildingsRepository(db.buildingsDao()))()

        return Result.success()
    }
}