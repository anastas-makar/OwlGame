package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.LootApiService
import pro.progr.owlgame.domain.model.PouchItemsModel
import pro.progr.owlgame.domain.repository.LootRepository
import javax.inject.Inject

class LootRepositoryImpl @Inject constructor(
    private val apiService: LootApiService
) : LootRepository {
    override suspend fun claimExpeditionLoot(expeditionId: String): Result<PouchItemsModel> {
        return try {
            val response = apiService.getLoot(expeditionId)
            if (response.isSuccessful) {
                val inPouch = response.body()?.toDomain() ?: PouchItemsModel()
                Result.success(inPouch)
            } else {
                Result.failure(Exception("Failed to load loot: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}