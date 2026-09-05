package pro.progr.owlgame.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import kotlinx.coroutines.CancellationException
import pro.progr.authapi.AuthInterface
import pro.progr.owlgame.BuildConfig
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.repository.impl.GameSyncRepositoryImpl
import pro.progr.owlgame.data.repository.impl.ImageRepositoryImpl
import pro.progr.owlgame.data.web.RetrofitProvider
import pro.progr.owlgame.data.web.sync.GameSyncApiService
import pro.progr.owlgame.domain.model.GameSyncResult

/**
 * Performs one game backup/restore pass immediately in the current coroutine.
 * Authorization is checked by the application before this function is called.
 */
suspend fun runGameSync(
    applicationContext: Context,
    auth: AuthInterface
): GameSyncResult {
    val db = OwlGameDatabase.getDatabase(applicationContext)
    val api = RetrofitProvider.provideRetrofit(
        BuildConfig.API_BASE_URL,
        auth,
        db.appMetaDao()
    ).create(GameSyncApiService::class.java)

    val syncRepository = GameSyncRepositoryImpl(
        db = db,
        syncDao = db.gameSyncDao(),
        outboxDao = db.outboxDao(),
        appMetaDao = db.appMetaDao(),
        apiService = api,
        imageRepository = ImageRepositoryImpl(applicationContext)
    )

    return syncRepository.sync()
}

suspend fun doGameSyncWork(
    applicationContext: Context,
    auth: AuthInterface
): ListenableWorker.Result = try {
    val result = runGameSync(applicationContext, auth)
    Log.d("GameSync", "Sync result: $result")
    ListenableWorker.Result.success()
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Log.e("GameSync", "Game sync failed", e)
    // A temporary network/server failure must keep the outbox intact.
    ListenableWorker.Result.retry()
}
