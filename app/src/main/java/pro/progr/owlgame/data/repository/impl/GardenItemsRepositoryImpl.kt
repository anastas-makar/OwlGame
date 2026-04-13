package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.dao.GardenItemsDao
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.GardenItemModel
import pro.progr.owlgame.domain.model.GardenType
import pro.progr.owlgame.domain.repository.GardenItemsRepository
import javax.inject.Inject

class GardenItemsRepositoryImpl @Inject constructor(
    private val gardenItemsDao: GardenItemsDao
) : GardenItemsRepository {
    override suspend fun insert(gardenItems: List<GardenItemModel>) {
        gardenItemsDao.insert(gardenItems.map {it.toData()})
    }

    override fun observeByGardenId(gardenId: String) : Flow<List<GardenItemModel>> {
        return gardenItemsDao.observeByGardenId(gardenId).map{ it.map{ item -> item.toDomain() } }
    }

    override fun updatePos(id: String, x: Float, y: Float) {
        gardenItemsDao. updatePosition(id, x, y)
    }

    override fun getAvailableGardenItems(gardenType: GardenType) : Flow<List<GardenItemModel>> {
        return gardenItemsDao.getAvailable(gardenType.toData()).map{ it.map{ item -> item.toDomain() } }
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