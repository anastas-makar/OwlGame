package pro.progr.owlgame.data.repository

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingWithAnimal
import pro.progr.owlgame.data.db.BuildingWithAnimalDao
import pro.progr.owlgame.data.db.BuildingsDao
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.GardensDao
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.RoomEntity
import pro.progr.owlgame.data.db.RoomsDao
import javax.inject.Inject

class BuildingsRepository @Inject constructor(
    private val db: OwlGameDatabase,
    private val buildingsDao : BuildingsDao,
    private val gardensDao: GardensDao,
    private val roomsDao: RoomsDao,
    private val buildingWithAnimalDao: BuildingWithAnimalDao
    ) {
   fun getAvailableBuildings() : Flow<List<Building>> {

       return buildingsDao.getAvailable()
   }

    fun getBuildingsWithAnimals(mapId : String) : Flow<Map<String, BuildingWithAnimal>> {
        return buildingWithAnimalDao.getBuildingsWithAnimals(mapId).map {
            it.associateBy { building ->
                building.building.id
            }
        }
    }

    fun getBuildingsWithAnimals() : Flow<Map<String, BuildingWithAnimal>> {
        return buildingWithAnimalDao.getBuildingsWithAnimals().map {
            it.associateBy { building ->
                building.building.id
            }
        }
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

    suspend fun saveBuildingsBundle(
        buildings: List<Building>,
        gardens: List<Garden>,
        rooms: List<RoomEntity>
    ) = db.withTransaction {
        buildingsDao.insert(buildings)   // важно: сначала здания
        gardensDao.insert(gardens)       // потом зависимые сущности
        roomsDao.insert(rooms)
    }
}