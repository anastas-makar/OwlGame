package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Enemy
import pro.progr.owlgame.data.db.Expedition
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.db.MapWithData
import pro.progr.owlgame.presentation.ui.model.MapData

interface MapsRepository {

    fun getMaps(): Flow<List<MapData>>

    fun getMapsWithUninhabitedBuildings() : Flow<List<MapWithData>>

    fun getMapById(id: String): Flow<MapWithData?>

    suspend fun setTown(name: String, mapId: String)

    suspend fun saveMaps(maps: List<MapEntity>,
                         expeditions: List<Expedition> = emptyList(),
                         enemies: List<Enemy> = emptyList())

}

