package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.db.PlantsDao
import javax.inject.Inject

class PlantsRepository @Inject constructor(
    private val plantsDao: PlantsDao
) {

    suspend fun insert(plants: List<Plant>) {
        plantsDao.insert(plants)
    }

    suspend fun markDeleted(id: String) = plantsDao.markDeleted(id)

    fun observeByGardenId(gardenId: String) : Flow<List<Plant>> {
        return plantsDao.observeByGardenId(gardenId)
    }

    fun getAvailablePlants() : Flow<List<Plant>> {
        return plantsDao.getAvailable()
    }

    fun updatePos(id: String, x: Float, y: Float) {
        plantsDao.updatePosition(id, x, y)
    }

    fun setPlant(itemId: String, gardenId: String) {
        plantsDao.setToGarden(itemId, gardenId)
    }

    suspend fun addReadinessToAllPlanted(delta: Float) {
        plantsDao.addReadinessToAllPlanted(delta)
    }
}