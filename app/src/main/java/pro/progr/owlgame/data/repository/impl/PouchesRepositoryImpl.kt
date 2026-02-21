package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.MapApiService
import pro.progr.owlgame.data.web.Pouch
import pro.progr.owlgame.data.web.inpouch.InPouch
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

class PouchesRepositoryImpl
    @Inject constructor(private val apiService: MapApiService,
                                                private val prefs: OwlPreferences,
                                                private val clock: Clock,
                                                @Named("apiKey") private val apiKey: String)
    : PouchesRepository {

    override suspend fun getPouches(): Result<List<Pouch>> {
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

    override suspend fun getInPouch(pouchId: String): Result<InPouch> {
        return try {
            val response = apiService.getInPouch(pouchId, apiKey)
            if (response.isSuccessful) {
                prefs.setLastPouchOpenDay(LocalDate.now(clock).toEpochDay())
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

