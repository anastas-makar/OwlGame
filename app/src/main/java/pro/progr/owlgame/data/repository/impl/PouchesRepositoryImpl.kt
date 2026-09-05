package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.CancellationException
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.data.web.LootApiService
import pro.progr.owlgame.domain.model.PouchItemsModel
import pro.progr.owlgame.domain.model.PouchOfferModel
import pro.progr.owlgame.domain.repository.PouchesRepository
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class PouchesRepositoryImpl
    @Inject constructor(
        private val apiService: LootApiService,
        private val prefs: OwlPreferences,
        private val clock: Clock
    )
    : PouchesRepository {

    override suspend fun getPouchOffer(): Result<PouchOfferModel> {
        return try {
            val response = apiService.getPouchOffer()
            if (response.isSuccessful) {
                val offer = requireNotNull(response.body()) {
                    "Empty pouch offer body"
                }
                require(offer.pouchId.isNotBlank()) {
                    "Pouch offer has an empty pouchId"
                }
                require(offer.imageUrls.isNotEmpty() && offer.imageUrls.none(String::isBlank)) {
                    "Pouch offer has no valid imageUrls"
                }
                Result.success(offer.toDomain())
            } else {
                Result.failure(
                    IllegalStateException(
                        "Failed to load pouch offer: HTTP ${response.code()}: " +
                            response.errorBody()?.string().orEmpty()
                    )
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getInPouch(pouchId: String): Result<PouchItemsModel> {
        return try {
            require(pouchId.isNotBlank()) { "pouchId must not be blank" }
            val response = apiService.getInPouch(pouchId)
            if (response.isSuccessful) {
                val inPouch = requireNotNull(response.body()) {
                    "Empty pouch opening body"
                }
                val result = inPouch.toDomain()
                prefs.setLastPouchOpenDay(LocalDate.now(clock).toEpochDay())
                Result.success(result)
            } else {
                Result.failure(
                    IllegalStateException(
                        "Failed to open pouch: HTTP ${response.code()}: " +
                            response.errorBody()?.string().orEmpty()
                    )
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
