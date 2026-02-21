package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.GardenType

interface GardenItemsRepository {
    suspend fun insert(gardenItems: List<GardenItem>)

    fun observeByGardenId(gardenId: String) : Flow<List<GardenItem>>

    fun updatePos(id: String, x: Float, y: Float)

    fun getAvailableGardenItems(gardenType: GardenType) : Flow<List<GardenItem>>

    suspend fun setGardenItem(id: String, gardenId: String)

    suspend fun addReadinessToAllPlanted(delta: Float)

    suspend fun flushReadinessForItem(itemId: String)
}