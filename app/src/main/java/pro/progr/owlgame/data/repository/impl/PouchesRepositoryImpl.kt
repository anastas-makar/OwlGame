package pro.progr.owlgame.data.repository.impl

import android.util.Log
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.data.web.MapApiService
import pro.progr.owlgame.data.web.Pouch
import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.domain.model.PouchModel
import pro.progr.owlgame.domain.repository.PouchesRepository
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

    override suspend fun getPouches(): Result<List<PouchModel>> {
        return try {
            val response = apiService.getPouches(apiKey)
            if (response.isSuccessful) {
                val pouchUrls = response.body() ?: emptyList()
                val pouches = pouchUrls.map { pouchUrl -> Pouch("todo", pouchUrl) }
                Result.success(pouches.map {it.toDomain()})
            } else {
                Log.e("pouch", "" + response.errorBody()?.string())
                Result.failure(Exception("Failed to load pouches: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Log.e("pouch error", e.message.toString())
                Result.failure(e)
        }
    }

    override suspend fun getInPouch(pouchId: String): Result<InPouchModel> {
        return try {
            val response = apiService.getInPouch(pouchId, apiKey)
            if (response.isSuccessful) {
                prefs.setLastPouchOpenDay(LocalDate.now(clock).toEpochDay())
                val inPouch = response.body()?.toDomain() ?: InPouchModel()
                Result.success(inPouch)
            } else {
                Result.failure(Exception("Failed to load inPouch: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

