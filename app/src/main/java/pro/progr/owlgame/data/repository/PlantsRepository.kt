package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.db.PlantsDao
import javax.inject.Inject

class PlantsRepository @Inject constructor(
    private val plantsDao: PlantsDao
) {

    suspend fun insert(plants: List<Plant>) {
        plantsDao.insert(plants)
    }
}