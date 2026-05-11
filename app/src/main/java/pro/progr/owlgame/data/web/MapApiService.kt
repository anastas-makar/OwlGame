package pro.progr.owlgame.data.web

import pro.progr.owlgame.data.web.inpouch.ExpeditionInPouch
import pro.progr.owlgame.data.web.inpouch.InPouch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApiService {
    @GET("pouches")
    suspend fun getPouches(): Response<List<String>>
    @GET("inPouch")
    suspend fun getInPouch(
        @Query("pouchId") pouchId: String
    ): Response<InPouch>
    @GET("loot")
    suspend fun getLoot(
        @Query("expeditionId") expeditionId: String
    ): Response<InPouch>

    @GET("newExpedition")
    suspend fun getNewExpedition(
        @Query("mapId") mapId: String
    ): Response<ExpeditionInPouch>
}