package pro.progr.owlgame.data.web

import pro.progr.owlgame.data.web.pouchitems.ExpeditionInPouch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApiService {
    @GET("newExpedition")
    suspend fun getNewExpedition(
        @Query("mapId") mapId: String
    ): Response<ExpeditionInPouch>
}