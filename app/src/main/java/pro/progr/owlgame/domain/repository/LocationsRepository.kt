package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.LocationWithScenesModel

interface LocationsRepository {
    suspend fun insert(locations: List<LocationWithScenesModel>)

    fun getLocationsWithScenesByMapId(mapId: String): Flow<List<LocationWithScenesModel>>

    fun getAvailableLocations(): Flow<List<LocationWithScenesModel>>

    suspend fun placeLocationOnMap(
        locationId: String,
        mapId: String,
        x: Float,
        y: Float
    )

    suspend fun updateLocationSlot(
        locationId: String,
        x: Float,
        y: Float
    )

    suspend fun completeQuestWithScenePatch(
        locationSceneId: String,
        imageUrl: String,
        imageKey: String,
        description: String
    )

    suspend fun completeQuestWithoutScenePatch(locationSceneId: String)
}
