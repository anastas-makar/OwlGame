package pro.progr.owlgame.data.web

import retrofit2.Response
import retrofit2.http.GET

interface AnimalApiService {
    @GET("animal")
    suspend fun getAnimal(
    ): Response<AnimalApiModel?>
}