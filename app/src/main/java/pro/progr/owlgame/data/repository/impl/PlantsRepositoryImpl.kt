package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.db.PlantsDao
import pro.progr.owlgame.data.repository.PlantsRepository
import javax.inject.Inject

class PlantsRepositoryImpl @Inject constructor(
    private val plantsDao: PlantsDao
) : PlantsRepository {

    override suspend fun insert(plants: List<Plant>) {
        plantsDao.insert(plants)
    }

    override suspend fun markDeleted(id: String) = plantsDao.markDeleted(id)

    override fun observeByGardenId(gardenId: String) : Flow<List<Plant>> {
        return plantsDao.observeByGardenId(gardenId)
    }

    override fun getAvailablePlants() : Flow<List<Plant>> {
        return plantsDao.getAvailable()
    }

    override fun updatePos(id: String, x: Float, y: Float) {
        plantsDao.updatePosition(id, x, y)
    }

    override fun setPlant(itemId: String, gardenId: String) {
        plantsDao.setToGarden(itemId, gardenId)
    }

    override suspend fun addReadinessToAllPlanted(delta: Float) {
        plantsDao.addReadinessToAllPlanted(delta)
    }
}