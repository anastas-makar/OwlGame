package pro.progr.owlgame.data.web

import pro.progr.owlgame.data.web.pouchitems.ExpeditionInPouch
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface MapApiService {
    @POST("maps/{mapId}/expeditions")
    suspend fun getNewExpedition(
        @Path("mapId") mapId: String
    ): Response<ExpeditionInPouch>
}
