package pro.progr.owlgame.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import pro.progr.authapi.AuthInterface
import pro.progr.owlgame.BuildConfig
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.repository.impl.GameSyncRepositoryImpl
import pro.progr.owlgame.data.repository.impl.ImageRepositoryImpl
import pro.progr.owlgame.data.web.RetrofitProvider
import pro.progr.owlgame.data.web.sync.GameSyncApiService

suspend fun doGameSyncWork(
    applicationContext: Context,
    auth: AuthInterface
): ListenableWorker.Result = try {
    val db = OwlGameDatabase.getDatabase(applicationContext)
    val api = RetrofitProvider.provideRetrofit(
        BuildConfig.API_BASE_URL,
        auth
    ).create(GameSyncApiService::class.java)

    val syncRepository = GameSyncRepositoryImpl(
        db = db,
        syncDao = db.gameSyncDao(),
        outboxDao = db.outboxDao(),
        appMetaDao = db.appMetaDao(),
        apiService = api,
        imageRepository = ImageRepositoryImpl(applicationContext)
    )

    val result = syncRepository.sync()
    Log.d("GameSync", "Sync result: $result")
    ListenableWorker.Result.success()
} catch (e: Exception) {
    Log.e("GameSync", "Game sync failed", e)
    // Для backup/restore временная ошибка сети не должна терять outbox.
    ListenableWorker.Result.retry()
}
