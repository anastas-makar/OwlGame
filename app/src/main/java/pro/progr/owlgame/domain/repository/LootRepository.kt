package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.InPouchModel

interface LootRepository {
    suspend fun getLoot(expeditionId: String) : Result<InPouchModel>
}