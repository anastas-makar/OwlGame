package pro.progr.owlgame.data.web

import pro.progr.owlgame.data.db.Animal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimalApiService {
    @GET("animal")
    suspend fun getAnimal(
    ): Response<Animal?>
}