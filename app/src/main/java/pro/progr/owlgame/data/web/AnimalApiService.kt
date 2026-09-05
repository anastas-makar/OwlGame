package pro.progr.owlgame.data.web

import retrofit2.Response
import retrofit2.http.POST

interface AnimalApiService {
    @POST("animals/visitor")
    suspend fun getAnimal(
    ): Response<AnimalApiModel?>
}
