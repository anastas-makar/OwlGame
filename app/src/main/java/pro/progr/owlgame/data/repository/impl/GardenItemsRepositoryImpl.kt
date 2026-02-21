package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.GardenItemsDao
import pro.progr.owlgame.data.db.GardenType
import pro.progr.owlgame.data.repository.GardenItemsRepository
import javax.inject.Inject

class GardenItemsRepositoryImpl @Inject constructor(
    private val gardenItemsDao: GardenItemsDao
) : GardenItemsRepository {
    override suspend fun insert(gardenItems: List<GardenItem>) {
        gardenItemsDao.insert(gardenItems)
    }

    override fun observeByGardenId(gardenId: String) : Flow<List<GardenItem>> {
        return gardenItemsDao.observeByGardenId(gardenId)
    }

    override fun updatePos(id: String, x: Float, y: Float) {
        gardenItemsDao. updatePosition(id, x, y)
    }

    override fun getAvailableGardenItems(gardenType: GardenType) : Flow<List<GardenItem>> {
        return gardenItemsDao.getAvailable(gardenType)
    }

    override suspend fun setGardenItem(id: String, gardenId: String) {
        gardenItemsDao.setToGarden(id, gardenId)
    }

    override suspend fun addReadinessToAllPlanted(delta: Float) {
        gardenItemsDao.addReadinessToAllPlanted(delta)
    }

    override suspend fun flushReadinessForItem(itemId: String) {
        gardenItemsDao.flushReadinessForItem(itemId)
    }
}