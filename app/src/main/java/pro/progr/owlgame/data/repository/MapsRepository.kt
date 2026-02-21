package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.db.MapWithData
import pro.progr.owlgame.presentation.ui.model.MapData

interface MapsRepository {

    fun getMaps(): Flow<List<MapData>>

    fun getMapsWithUninhabitedBuildings() : Flow<List<MapWithData>>

    fun getMapById(id: String): Flow<MapWithData?>

    suspend fun saveMaps(maps: List<MapEntity>)

    suspend fun setTown(name: String, mapId: String)

}

