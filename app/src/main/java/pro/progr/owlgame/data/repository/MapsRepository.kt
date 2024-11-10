package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.web.MapApiService
import pro.progr.owlgame.data.web.Map
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MapsRepository @Inject constructor(private val apiService: MapApiService,
                                         @Named("apiKey") private val apiKey: String) {

    suspend fun getMaps(): Result<List<Map>> {
        return try {
            val response = apiService.getMaps(apiKey)
            if (response.isSuccessful) {
                val mapUrls = response.body() ?: emptyList()
                val maps = mapUrls.map { mapUrl -> Map("", "", mapUrl) }
                Result.success(maps)
            } else {
                Result.failure(Exception("Failed to load maps: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
