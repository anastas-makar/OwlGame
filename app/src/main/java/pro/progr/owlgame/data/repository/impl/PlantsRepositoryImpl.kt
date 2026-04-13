package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.dao.PlantsDao
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.PlantModel
import pro.progr.owlgame.domain.repository.PlantsRepository
import javax.inject.Inject

class PlantsRepositoryImpl @Inject constructor(
    private val plantsDao: PlantsDao
) : PlantsRepository {

    override suspend fun insert(plants: List<PlantModel>) {
        plantsDao.insert(plants.map { it.toData()})
    }

    override suspend fun markDeleted(id: String) = plantsDao.markDeleted(id)

    override fun observeByGardenId(gardenId: String) : Flow<List<PlantModel>> {
        return plantsDao.observeByGardenId(gardenId).map { it.map { plant -> plant.toDomain() } }
    }

    override fun getAvailablePlants() : Flow<List<PlantModel>> {
        return plantsDao.getAvailable().map { it.map { plant -> plant.toDomain() } }
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