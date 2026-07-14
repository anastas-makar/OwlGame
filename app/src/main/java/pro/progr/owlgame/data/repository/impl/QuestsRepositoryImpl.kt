package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.db.dao.LocationScenesDao
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.QuestApiService
import pro.progr.owlgame.data.web.quest.CompleteQuestRequest
import pro.progr.owlgame.domain.model.QuestCompletionResultModel
import pro.progr.owlgame.domain.model.QuestModel
import pro.progr.owlgame.domain.model.QuestScenePatchModel
import pro.progr.owlgame.domain.repository.QuestsRepository
import javax.inject.Inject

class QuestsRepositoryImpl @Inject constructor(
    private val questApiService: QuestApiService,
    private val locationScenesDao: LocationScenesDao
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

    override suspend fun completeQuest(
        questId: String,
        locationSceneId: String,
        endingId: String
    ): Result<QuestCompletionResultModel> {
        return try {
            val response = questApiService.completeQuest(
                questId = questId,
                request = CompleteQuestRequest(
                    locationSceneId = locationSceneId,
                    endingId = endingId
                )
            )

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                return Result.failure(
                    IllegalStateException(
                        "Quest completion failed: ${response.code()}, $errorBody"
                    )
                )
            }

            val body = response.body()
                ?: return Result.failure(
                    IllegalStateException("Quest completion response is empty")
                )

            val scenePatch = body.scenePatch
                ?: return Result.failure(
                    IllegalStateException(
                        "Quest completion response has no scenePatch. Server error: ${body.error ?: body}"
                    )
                )

            locationScenesDao.applyQuestResult(
                sceneId = locationSceneId,
                imageUrl = scenePatch.imageUrl,
                description = scenePatch.description
            )

            Result.success(
                QuestCompletionResultModel(
                    questId = body.questId ?: questId,
                    locationSceneId = body.locationSceneId ?: locationSceneId,
                    endingId = body.endingId ?: endingId,
                    scenePatch = QuestScenePatchModel(
                        description = scenePatch.description,
                        imageUrl = scenePatch.imageUrl
                    ),
                    lootAvailable = body.lootAvailable,
                    lootButtonText = body.lootButtonText
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}