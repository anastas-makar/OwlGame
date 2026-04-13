package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.GardenItemModel
import pro.progr.owlgame.domain.model.GardenType

interface GardenItemsRepository {
    suspend fun insert(gardenItems: List<GardenItemModel>)

    fun observeByGardenId(gardenId: String) : Flow<List<GardenItemModel>>

    fun updatePos(id: String, x: Float, y: Float)

    fun getAvailableGardenItems(gardenType: GardenType) : Flow<List<GardenItemModel>>

    suspend fun setGardenItem(id: String, gardenId: String)

    suspend fun addReadinessToAllPlanted(delta: Float)

    suspend fun flushReadinessForItem(itemId: String)
}