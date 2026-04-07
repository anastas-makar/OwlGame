package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.db.embedded.ExpeditionWithData
import pro.progr.owlgame.data.model.StartExpeditionRequest

interface ExpeditionsRepository {

    fun getExpeditionWithData(mapId: String) : Flow<ExpeditionWithData>

    suspend fun startExpedition(
        diamondDao: PurchaseInterface,
        request: StartExpeditionRequest
    ): Result<Unit>

    suspend fun getById(expeditionId: String) : Expedition?

    suspend fun updateAnimalId(
        expeditionId: String,
        animalId: String?
    ): Int

}

