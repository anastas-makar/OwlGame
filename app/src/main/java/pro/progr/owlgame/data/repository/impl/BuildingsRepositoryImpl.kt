package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.BuildingWithAnimalDao
import pro.progr.owlgame.data.db.dao.BuildingWithDataDao
import pro.progr.owlgame.data.db.dao.BuildingsDao
import pro.progr.owlgame.data.db.dao.GardensDao
import pro.progr.owlgame.data.db.dao.RoomsDao
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.BuildingWithDataModel
import pro.progr.owlgame.domain.repository.BuildingsRepository
import javax.inject.Inject

class BuildingsRepositoryImpl @Inject constructor(
    private val db: OwlGameDatabase,
    private val buildingsDao : BuildingsDao,
    private val gardensDao: GardensDao,
    private val roomsDao: RoomsDao,
    private val buildingWithAnimalDao: BuildingWithAnimalDao,
    private val buildingWithDataDao: BuildingWithDataDao
    ) : BuildingsRepository {
   override fun getAvailableBuildings() : Flow<List<BuildingModel>> {

       return buildingsDao.getAvailable().map { it.map { b -> b.toDomain() } }
   }

    override fun getBuildingsWithAnimals(mapId : String) : Flow<Map<String, BuildingWithAnimalModel>> {
        return buildingWithAnimalDao.getBuildingsWithAnimals(mapId).map {
            it.map { b -> b.toDomain() }.associateBy { building ->
                building.id
            }
        }
    }

    override fun getBuildingsWithAnimals() : Flow<Map<String, BuildingWithAnimalModel>> {
        return buildingWithAnimalDao.getBuildingsWithAnimals().map {
            it.map { b -> b.toDomain() }.associateBy { building ->
                building.id
            }
        }
    }

    override fun countUninhabited() : Long {
        return buildingsDao.countUninhabited()
    }

    override fun updateAnimalId(buildingId: String, animalId: String): Int {
        return buildingsDao.updateAnimalId(buildingId, animalId)
    }

    override suspend fun saveBuildings(buildings: List<BuildingModel>) {
        buildingsDao.insert(buildings.map { it.toData() })
    }

    override suspend fun saveBuildingsBundle(
        buildings: List<BuildingWithDataModel>
    ) {

        val buildingsForLocal = buildings.map { b ->
            Building(
                id = b.id,
                name = b.name,
                imageUrl = b.imageUrl,
                price = b.price,
                type = b.type.toData()
            )
        }

        val roomsForLocal = buildings.flatMap { b ->
            b.rooms.map { r ->
                r.toData()
            }
        }

        val gardensForLocal = buildings.flatMap { b ->
            b.gardens.map { g ->
                g.toData()
            }
        }

        db.withTransaction {
            buildingsDao.insert(buildingsForLocal)   // важно: сначала здания
            gardensDao.insert(gardensForLocal)       // потом зависимые сущности
            roomsDao.insert(roomsForLocal)
        }

    }

    override fun observe(buildingId: String): Flow<BuildingWithDataModel> =
        buildingWithDataDao.observeBuildingWithData(buildingId).map { it.toDomain() }
}