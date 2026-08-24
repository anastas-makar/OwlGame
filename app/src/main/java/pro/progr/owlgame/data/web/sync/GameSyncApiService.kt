package pro.progr.owlgame.data.web.sync

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GameSyncApiService {
    @POST("sync/game/backup")
    suspend fun backup(@Body request: GameBackupRequest): Response<GameBackupResponse>

    @POST("sync/game/restore")
    suspend fun restore(@Body request: GameRestoreRequest): Response<GameRestoreResponse>
}
