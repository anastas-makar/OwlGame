package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.LocationScenesDao
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.QuestApiService
import pro.progr.owlgame.data.web.quest.CompleteQuestRequest
import pro.progr.owlgame.domain.model.QuestModel
import pro.progr.owlgame.domain.repository.QuestsRepository
import javax.inject.Inject

class QuestsRepositoryImpl @Inject constructor(
    private val questApiService: QuestApiService,
    private val locationScenesDao: LocationScenesDao,
    private val database: OwlGameDatabase
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
    ): Result<Unit> {
        return try {
            val response = questApiService.completeQuest(
                questId = questId,
                request = CompleteQuestRequest(
                    locationSceneId = locationSceneId,
                    endingId = endingId
                )
            )

            if (!response.isSuccessful) {
                return Result.failure(
                    IllegalStateException("Quest completion failed: ${response.code()}")
                )
            }

            val body = response.body()
                ?: return Result.failure(IllegalStateException("Quest completion response is empty"))

            body.scenePatch?.let { scenePatch ->
                locationScenesDao.applyQuestResult(
                    sceneId = locationSceneId,
                    imageUrl = scenePatch.imageUrl,
                    description = scenePatch.description
                )
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}