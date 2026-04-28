package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.model.ExpeditionModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.domain.model.StartExpeditionRequest

interface ExpeditionsRepository {

    fun getExpeditionWithData(mapId: String) : Flow<ExpeditionWithDataModel?>

    suspend fun startExpedition(
        diamondDao: PurchaseInterface,
        request: StartExpeditionRequest
    ): Result<Unit>

    suspend fun getById(expeditionId: String) : ExpeditionModel?

    suspend fun updateAnimalId(
        expeditionId: String,
        animalId: String?
    ): Int

    suspend fun resolveExpeditionProgress(expeditionId: String): Result<Unit>

}

