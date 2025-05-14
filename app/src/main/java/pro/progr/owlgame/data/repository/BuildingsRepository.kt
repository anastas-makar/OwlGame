package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingsDao
import javax.inject.Inject

class BuildingsRepository @Inject constructor(private val buildingsDao : BuildingsDao) {
   fun getAvailableBuildings() : Flow<List<Building>> {

       return buildingsDao.getAvailable()
   }

    fun countUninhabited() : Long {
        return buildingsDao.countUninhabited()
    }

    fun updateAnimalId(buildingId: String, animalId: String): Int {
        return buildingsDao.updateAnimalId(buildingId, animalId)
    }

    suspend fun saveBuildings(buildings: List<Building>) {
        buildingsDao.insert(buildings)
    }
}