package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.CancellationException
import pro.progr.diagnostics.Diagnostics
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.LootApiService
import pro.progr.owlgame.data.web.quest.QuestLootRequest
import pro.progr.owlgame.diagnostics.OwlGameApiException
import pro.progr.owlgame.domain.model.PouchItemsModel
import pro.progr.owlgame.domain.repository.LootRepository
import pro.progr.owlgame.diagnostics.reportOwlGameFailure
import javax.inject.Inject

class LootRepositoryImpl @Inject constructor(
    private val apiService: LootApiService,
    private val diagnostics: Diagnostics
) : LootRepository {
    override suspend fun claimExpeditionLoot(expeditionId: String): Result<PouchItemsModel> {
        return try {
            val response = apiService.getLoot(expeditionId)
            val result = if (response.isSuccessful) {
                val inPouch = response.body()?.toDomain() ?: PouchItemsModel()
                Result.success(inPouch)
            } else {
                Result.failure(OwlGameApiException("expedition_loot", response.code()))
            }
            result.reportOwlGameFailure(diagnostics, "owl_game_expedition_loot_failure")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            diagnostics.reportOwlGameFailure("owl_game_expedition_loot_failure", e)
            Result.failure(e)
        }
    }

    override suspend fun claimQuestLoot(
        questId: String,
        endingId: String
    ): Result<PouchItemsModel> {
        return try {
            val response = apiService.getQuestLoot(
                questId = questId,
                request = QuestLootRequest(endingId = endingId)
            )

            val result = if (response.isSuccessful) {
                val inPouch = response.body()?.toDomain() ?: PouchItemsModel()
                Result.success(inPouch)
            } else {
                Result.failure(
                    OwlGameApiException("quest_loot", response.code())
                )
            }
            result.reportOwlGameFailure(diagnostics, "owl_game_quest_loot_failure")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            diagnostics.reportOwlGameFailure("owl_game_quest_loot_failure", e)
            Result.failure(e)
        }
    }
}
