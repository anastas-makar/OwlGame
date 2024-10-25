package pro.progr.owlgame.data.web

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApiService {
    @GET("maps")
    suspend fun getMaps(
        @Query("apiKey") apiKey: String
    ): Response<List<Map>>
}