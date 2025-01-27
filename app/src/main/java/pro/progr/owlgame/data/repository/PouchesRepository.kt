package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.web.MapApiService
import pro.progr.owlgame.data.web.Pouch
import pro.progr.owlgame.data.web.inpouch.InPouch
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PouchesRepository @Inject constructor(private val apiService: MapApiService,
                                            @Named("apiKey") private val apiKey: String) {

    suspend fun getPouches(): Result<List<Pouch>> {
        return try {
            val response = apiService.getPouches(apiKey)
            if (response.isSuccessful) {
                val mapUrls = response.body() ?: emptyList()
                val maps = mapUrls.map { pouchUrl -> Pouch("todo", pouchUrl) }
                Result.success(maps)
            } else {
                Result.failure(Exception("Failed to load pouches: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getInPouch(pouchId: String): Result<InPouch> {
        return try {
            val response = apiService.getInPouch(pouchId, apiKey)
            if (response.isSuccessful) {
                val inPouch = response.body() ?: InPouch()
                Result.success(inPouch)
            } else {
                Result.failure(Exception("Failed to load inPouch: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

