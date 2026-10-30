package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.CancellationException
import pro.progr.diagnostics.Diagnostics
import pro.progr.owlgame.diagnostics.reportOwlGameFailure
import pro.progr.owlgame.diagnostics.OwlGameApiException
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
        private val clock: Clock,
        private val diagnostics: Diagnostics
    )
    : PouchesRepository {

    override suspend fun getPouchOffer(): Result<PouchOfferModel> {
        return try {
            val response = apiService.getPouchOffer()
            val result = if (response.isSuccessful) {
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
                Result.failure(OwlGameApiException("pouch_offer", response.code()))
            }
            result.reportOwlGameFailure(diagnostics, "owl_game_pouch_offer_failure")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            diagnostics.reportOwlGameFailure("owl_game_pouch_offer_failure", e)
            Result.failure(e)
        }
    }

    override suspend fun getInPouch(pouchId: String): Result<PouchItemsModel> {
        return try {
            require(pouchId.isNotBlank()) { "pouchId must not be blank" }
            val response = apiService.getInPouch(pouchId)
            val result = if (response.isSuccessful) {
                val inPouch = requireNotNull(response.body()) {
                    "Empty pouch opening body"
                }
                val result = inPouch.toDomain()
                prefs.setLastPouchOpenDay(LocalDate.now(clock).toEpochDay())
                Result.success(result)
            } else {
                Result.failure(OwlGameApiException("open_pouch", response.code()))
            }
            result.reportOwlGameFailure(diagnostics, "owl_game_open_pouch_failure")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            diagnostics.reportOwlGameFailure("owl_game_open_pouch_failure", e)
            Result.failure(e)
        }
    }
}
