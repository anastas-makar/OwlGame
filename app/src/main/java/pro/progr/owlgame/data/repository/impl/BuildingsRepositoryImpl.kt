package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingWithAnimal
import pro.progr.owlgame.data.db.BuildingWithAnimalDao
import pro.progr.owlgame.data.db.BuildingWithData
import pro.progr.owlgame.data.db.BuildingWithDataDao
import pro.progr.owlgame.data.db.BuildingsDao
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.GardensDao
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.RoomEntity
import pro.progr.owlgame.data.db.RoomsDao
import pro.progr.owlgame.data.repository.BuildingsRepository
import javax.inject.Inject

class BuildingsRepositoryImpl @Inject constructor(
    private val db: OwlGameDatabase,
    private val buildingsDao : BuildingsDao,
    private val gardensDao: GardensDao,
    private val roomsDao: RoomsDao,
    private val buildingWithAnimalDao: BuildingWithAnimalDao,
    private val buildingWithDataDao: BuildingWithDataDao
    ) : BuildingsRepository {
   override fun getAvailableBuildings() : Flow<List<Building>> {

       return buildingsDao.getAvailable()
   }

    override fun getBuildingsWithAnimals(mapId : String) : Flow<Map<String, BuildingWithAnimal>> {
        return buildingWithAnimalDao.getBuildingsWithAnimals(mapId).map {
            it.associateBy { building ->
                building.building.id
            }
        }
    }

    override fun getBuildingsWithAnimals() : Flow<Map<String, BuildingWithAnimal>> {
        return buildingWithAnimalDao.getBuildingsWithAnimals().map {
            it.associateBy { building ->
                building.building.id
            }
        }
    }

    override fun countUninhabited() : Long {
        return buildingsDao.countUninhabited()
    }

    override fun updateAnimalId(buildingId: String, animalId: String): Int {
        return buildingsDao.updateAnimalId(buildingId, animalId)
    }

    override suspend fun saveBuildings(buildings: List<Building>) {
        buildingsDao.insert(buildings)
    }

    override suspend fun saveBuildingsBundle(
        buildings: List<Building>,
        gardens: List<Garden>,
        rooms: List<RoomEntity>
    ) = db.withTransaction {
        buildingsDao.insert(buildings)   // важно: сначала здания
        gardensDao.insert(gardens)       // потом зависимые сущности
        roomsDao.insert(rooms)
    }

    override fun observe(buildingId: String): Flow<BuildingWithData> =
        buildingWithDataDao.observeBuildingWithData(buildingId)
}