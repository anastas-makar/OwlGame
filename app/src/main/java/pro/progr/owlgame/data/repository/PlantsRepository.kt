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

    fun observeByGardenId(gardenId: String) : Flow<List<Plant>> {
        TODO("Not yet implemented")
    }

    fun updatePos(id: String, x: Float, y: Float) {
        TODO("Not yet implemented")
    }
}