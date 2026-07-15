package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.QuestApiService
import pro.progr.owlgame.domain.model.QuestModel
import pro.progr.owlgame.domain.repository.QuestsRepository
import javax.inject.Inject

class QuestsRepositoryImpl @Inject constructor(
    private val questApiService: QuestApiService
) : QuestsRepository {

    override suspend fun loadQuest(questId: String): Result<QuestModel> {
        return try {
            val response = questApiService.getQuest(questId)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toDomain())
                } else {
                    Result.failure(IllegalStateException("Quest response is empty"))
                }
            } else {
                Result.failure(IllegalStateException("Quest loading failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}