package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingsDao
import javax.inject.Inject

class BuildingsRepository @Inject constructor(private val buildingsDao : BuildingsDao) {
   fun getAvailableHouses() : Flow<List<Building>> {

       return buildingsDao.getAvailable()
   }

    suspend fun insertForTest() {
        //todo: Это для теста
        buildingsDao.insert(listOf(
            Building("1",
                "какое-то",
                "test1"),
            Building("2",
                "какое-то",
                "test2"),
            Building("3",
                "какое-то",
                "test3"
            )
        ))
    }

   fun getBuildingsOnMap(mapId: String) : Flow<List<Building>> {
       //todo:
       return buildingsDao.getOnMap(mapId)
   }

   fun getAvailableFortresses() : Flow<Building> {
       TODO()
   }

    suspend fun saveBuildings(buildings: List<Building>) {
        buildingsDao.insert(buildings)
    }
}