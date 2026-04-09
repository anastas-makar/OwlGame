package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.BuildingWithDataModel

interface BuildingsRepository {
   fun getAvailableBuildings() : Flow<List<BuildingModel>>

    fun getBuildingsWithAnimals(mapId : String) : Flow<Map<String, BuildingWithAnimalModel>>

    fun getBuildingsWithAnimals() : Flow<Map<String, BuildingWithAnimalModel>>

    fun countUninhabited() : Long

    fun updateAnimalId(buildingId: String, animalId: String): Int

    suspend fun saveBuildings(buildings: List<BuildingModel>)

    suspend fun saveBuildingsBundle(
        buildings: List<BuildingWithDataModel>
    ) : List<BuildingWithDataModel>

    fun observe(buildingId: String): Flow<BuildingWithDataModel>
}