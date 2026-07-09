package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.QuestModel

interface QuestsRepository {

    suspend fun loadQuest(questId: String): Result<QuestModel>

    suspend fun completeQuest(
        questId: String,
        locationSceneId: String,
        endingId: String
    ): Result<Unit>
}