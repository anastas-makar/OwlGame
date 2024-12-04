package pro.progr.owlgame.data.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.data.web.MapApiService
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
                val maps = mapUrls.map { mapUrl -> Map("", "Болотистая местность", mapUrl) }
                Result.success(maps)
            } else {
                Result.failure(Exception("Failed to load maps: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getMapById(imageUrl: String): Flow<Map> {
        //временное решение, тут url вместо id
        return MutableStateFlow(Map(
            "",
            "Болотистая местность",
            Uri.decode(imageUrl)
        ))
    }
}

