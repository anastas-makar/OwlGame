package pro.progr.owlgame.data.web

import pro.progr.owlgame.data.web.inpouch.InPouch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApiService {
    @GET("maps")
    suspend fun getMaps(
        @Query("apiKey") apiKey: String
    ): Response<List<String>>
    @GET("pouches")
    suspend fun getPouches(
        @Query("apiKey") apiKey: String
    ): Response<List<String>>
    @GET("inPouch")
    suspend fun getInPouch(
        @Query("pouchId") pouchId: String,
        @Query("apiKey") apiKey: String
    ): Response<InPouch>
}