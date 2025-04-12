package pro.progr.owlgame.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
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
import pro.progr.owlgame.R
import pro.progr.owlgame.data.web.AnimalApiService

class AnimalBuildingsWorker(
    context: Context,
    workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.wtf("WORKER IS WORKING", LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()))

        val db = OwlGameDatabase.getDatabase(context = applicationContext)

        val animalDao = db.animalDao()

        Log.wtf("AnimalDao count searching: ", animalDao.countSearching().toString())

        val animalRepository = AnimalsRepository(db.animalDao(),
            RetrofitProvider.provideRetrofit(BuildConfig.API_BASE_URL)
                .create(AnimalApiService::class.java),
            BuildConfig.API_KEY)

        val animal = SearchAnimalsUseCase(
            animalRepository,
            BuildingsRepository(db.buildingsDao()))()

        if (animal != null) {
            animalRepository.saveAnimal(animal)
            Log.wtf("Животное ищет дом", animal.name)

            showNotification(animal.id, animal.name)
        }

        return Result.success()
    }

    private fun showNotification(animalId: String, animalName: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "animal_channel_id"
        val channel = NotificationChannel(
            channelId,
            "Animal Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val deepLinkUri = Uri.parse("owlgame://animal/$animalId")
        val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.test2)
            .setContentTitle("$animalName ищет дом")
            .setContentText("Нажми, чтобы узнать больше")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(animalId.hashCode(), notification)
    }

}