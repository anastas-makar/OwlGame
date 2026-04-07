package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.embedded.BuildingWithAnimal
import pro.progr.owlgame.data.db.embedded.BuildingWithData
import pro.progr.owlgame.data.db.entity.Garden
import pro.progr.owlgame.data.db.entity.RoomEntity

interface BuildingsRepository {
   fun getAvailableBuildings() : Flow<List<Building>>

    fun getBuildingsWithAnimals(mapId : String) : Flow<Map<String, BuildingWithAnimal>>

    fun getBuildingsWithAnimals() : Flow<Map<String, BuildingWithAnimal>>

    fun countUninhabited() : Long

    fun updateAnimalId(buildingId: String, animalId: String): Int

    suspend fun saveBuildings(buildings: List<Building>)

    suspend fun saveBuildingsBundle(
        buildings: List<Building>,
        gardens: List<Garden>,
        rooms: List<RoomEntity>
    )

    fun observe(buildingId: String): Flow<BuildingWithData>
}