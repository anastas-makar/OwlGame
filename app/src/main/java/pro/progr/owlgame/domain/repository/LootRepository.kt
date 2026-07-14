package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.PouchItemsModel

interface LootRepository {
    suspend fun claimExpeditionLoot(expeditionId: String) : Result<PouchItemsModel>

    suspend fun claimQuestLoot(
        questId: String,
        endingId: String
    ): Result<PouchItemsModel>
}