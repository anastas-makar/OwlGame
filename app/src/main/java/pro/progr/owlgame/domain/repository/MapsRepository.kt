package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.MapInPouchModel
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapWithBuildingsModel

interface MapsRepository {

    fun getMaps(): Flow<List<MapModel>>

    fun getMapsWithUninhabitedBuildings() : Flow<List<MapWithBuildingsModel>>

    fun getMapById(id: String): Flow<MapWithBuildingsModel?>

    suspend fun setTown(name: String, mapId: String)

    suspend fun saveMaps(mapModels: List<MapInPouchModel>)

}

