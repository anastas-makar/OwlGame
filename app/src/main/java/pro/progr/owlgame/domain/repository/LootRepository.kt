package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.InPouchModel

interface LootRepository {
    suspend fun claimExpeditionLoot(expeditionId: String) : Result<InPouchModel>
}