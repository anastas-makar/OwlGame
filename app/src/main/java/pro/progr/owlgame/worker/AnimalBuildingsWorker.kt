package pro.progr.owlgame.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.ListenableWorker
import pro.progr.authapi.AuthInterface
import pro.progr.owlgame.BuildConfig
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.data.repository.impl.AnimalsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.BuildingsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.ImageRepositoryImpl
import pro.progr.owlgame.data.web.AnimalApiService
import pro.progr.owlgame.data.web.RetrofitProvider
import pro.progr.owlgame.domain.usecase.SearchingAnimalUseCase
import java.time.Clock
import java.time.LocalDate
import java.time.LocalTime

private const val ANIMAL_CHANNEL_ID = "animal_channel_id"

suspend fun doAnimalBuildingsWork(
    applicationContext: Context,
    auth: AuthInterface
): ListenableWorker.Result = try {

    val prefs = OwlPreferences(applicationContext)

    val db = OwlGameDatabase.getDatabase(context = applicationContext)
    val animalDao = db.animalDao()

    val imagesRepository = ImageRepositoryImpl(
        context = applicationContext,
        baseUrl = BuildConfig.API_BASE_URL
    )

    val animalRepository = AnimalsRepositoryImpl(
        db.animalDao(),
        RetrofitProvider.provideRetrofit(
            BuildConfig.API_BASE_URL,
            auth
        ).create(AnimalApiService::class.java),
        Clock.systemDefaultZone()
    )

    Log.d("AnimalDao count searching: ", animalRepository.countAnimalsSearching().toString())

    SearchingAnimalUseCase(
        animalRepository,
        BuildingsRepositoryImpl(
            db, db.buildingsDao(),
            db.gardensDao(),
            db.roomsDao(),
            db.buildingWithAnimalDao(),
            db.buildingWithDataDao()
        ),
        imagesRepository
    )()?.let { animal ->
        Log.d("Животное ищет дом", animal.initialDisplayName)

        val currentEpochDay = LocalDate.now().toEpochDay()
        val savedId = prefs.getAnimalId()
        val savedEpochDay = prefs.getAnimalDay()

        if (savedId != null && savedEpochDay != -1L) {
            if (savedId == animal.id) {
                //Если животное уже было найдено раньше, то заново уведомление не показываем,
                // ждём два дня, потом отпускаем животное
                if (currentEpochDay - savedEpochDay >= 2) {
                    Log.wtf("Animal has been searching for more than 2 days", "Marking as gone")

                    animalDao.setGone(animal.id)
                    prefs.clearAnimalDayAndId()
                }
            } else {
                Log.e("Animal ID mismatch", "Expected $savedId, got ${animal.id}")

                // Сохраняем новые данные и шлём уведомление
                prefs.setAnimalIdAndDay(animal.id, currentEpochDay)

                showNotification(
                    applicationContext = applicationContext,
                    animalId = animal.id,
                    animalDisplayName = animal.initialDisplayName,
                    animalIconPath = animal.imagePath
                )
            }
        } else {
            prefs.setAnimalIdAndDay(animal.id, currentEpochDay)

            showNotification(
                applicationContext = applicationContext,
                animalId = animal.id,
                animalDisplayName = animal.initialDisplayName,
                animalIconPath = animal.imagePath
            )
        }

    }
        ?: prefs.clearAnimalDayAndId() //на всякий случай, чтобы несуществующее животное не застряло в prefs


    ListenableWorker.Result.success()
} catch (e: Exception) {
    Log.e("doAnimalBuildingsWork", "${e.message}")
    ListenableWorker.Result.failure()
}

private fun showNotification(
    applicationContext: Context,
    animalId: String,
    animalDisplayName: String,
    animalIconPath: String
) {
    val now = LocalTime.now()
    val start = LocalTime.of(9, 0)
    val end = LocalTime.of(21, 0)

    if (now.isBefore(start) || now.isAfter(end)) return

    val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channel = NotificationChannel(
        ANIMAL_CHANNEL_ID,
        applicationContext.getString(R.string.notification_channel_animals_name),
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = applicationContext.getString(R.string.notification_channel_animals_description)
    }

    notificationManager.createNotificationChannel(channel)

    val deepLinkUri = "owlgame://animal/$animalId".toUri()
    val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        animalId.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val bitmap = BitmapFactory.decodeFile(animalIconPath)

    val notification = NotificationCompat.Builder(applicationContext, ANIMAL_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_owl)
        .setLargeIcon(bitmap)
        .setContentTitle(
            applicationContext.getString(
                R.string.notification_animal_searching_title,
                animalDisplayName
            )
        )
        .setContentText(
            applicationContext.getString(R.string.notification_animal_searching_text)
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(animalId.hashCode(), notification)
}