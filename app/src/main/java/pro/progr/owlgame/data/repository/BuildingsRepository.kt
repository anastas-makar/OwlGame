package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingsDao
import javax.inject.Inject

class BuildingsRepository @Inject constructor(private val buildingsDao : BuildingsDao) {
   fun getAvailableHouses() : Flow<List<Building>> {

       return buildingsDao.getAvailable()
   }

    fun countUninhabited() : Long {
        return buildingsDao.countUninhabited()
    }

   fun getAvailableFortresses() : Flow<Building> {
       TODO()
   }

    suspend fun saveBuildings(buildings: List<Building>) {
        buildingsDao.insert(buildings)
    }
}