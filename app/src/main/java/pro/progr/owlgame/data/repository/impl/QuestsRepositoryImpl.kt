package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.CancellationException
import pro.progr.diagnostics.Diagnostics
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.QuestApiService
import pro.progr.owlgame.domain.model.QuestModel
import pro.progr.owlgame.domain.repository.QuestsRepository
import pro.progr.owlgame.diagnostics.OwlGameApiException
import pro.progr.owlgame.diagnostics.reportOwlGameFailure
import javax.inject.Inject

class QuestsRepositoryImpl @Inject constructor(
    private val questApiService: QuestApiService,
    private val diagnostics: Diagnostics
) : QuestsRepository {

    override suspend fun loadQuest(questId: String): Result<QuestModel> {
        return try {
            val response = questApiService.getQuest(questId)

            val result = if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toDomain())
                } else {
                    Result.failure(OwlGameApiException("quest_empty_response"))
                }
            } else {
                Result.failure(OwlGameApiException("quest_load", response.code()))
            }
            result.reportOwlGameFailure(diagnostics, "owl_game_quest_load_failure")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            diagnostics.reportOwlGameFailure("owl_game_quest_load_failure", e)
            Result.failure(e)
        }
    }
}
