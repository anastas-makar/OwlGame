package pro.progr.owlgame.data.web

import okhttp3.Interceptor
import okhttp3.Response
import pro.progr.owlgame.data.db.GAME_INSTANCE_ID_META_KEY
import pro.progr.owlgame.data.db.dao.AppMetaDao
import java.io.IOException

private const val GAME_INSTANCE_ID_HEADER = "X-Game-Instance-Id"

/** Adds the identifier of this local game database to every game API request. */
class GameInstanceIdInterceptor(
    private val appMetaDao: AppMetaDao
) : Interceptor {

    @Volatile
    private var cachedGameInstanceId: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val gameInstanceId = cachedGameInstanceId ?: synchronized(this) {
            cachedGameInstanceId ?: appMetaDao
                .getValueBlocking(GAME_INSTANCE_ID_META_KEY)
                ?.takeIf(String::isNotBlank)
                ?.also { cachedGameInstanceId = it }
                ?: throw IOException("Game database has no game_instance_id")
        }

        val request = chain.request()
            .newBuilder()
            .header(GAME_INSTANCE_ID_HEADER, gameInstanceId)
            .build()

        return chain.proceed(request)
    }
}
