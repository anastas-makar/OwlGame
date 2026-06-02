package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.LocationsDao
import pro.progr.owlgame.data.db.dao.LocationScenesDao
import pro.progr.owlgame.data.db.entity.Location
import pro.progr.owlgame.data.db.entity.LocationScene
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.mapper.toEntity
import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.domain.repository.LocationsRepository
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    val locationsDao: LocationsDao,
    val locationScenesDao: LocationScenesDao,
    val database: OwlGameDatabase
) : LocationsRepository {
    override suspend fun insert(locations: List<LocationWithScenesModel>) {
        val locationEntities = mutableListOf<Location>()
        val locationSceneEntities = mutableListOf<LocationScene>()

        for (locationWithScenes in locations) {
            locationEntities.add(locationWithScenes.toEntity(null))

            for (scene in locationWithScenes.scenes) {
                locationSceneEntities.add(scene.toData(locationWithScenes.id))
            }
        }

        database.withTransaction {
            locationsDao.insert(locationEntities)
            locationScenesDao.insert(locationSceneEntities)
        }
    }

    override fun getLocationsWithScenesByMapId(mapId: String): Flow<List<LocationWithScenesModel>> {
        return locationsDao.getWithScenesByMapId(mapId).map { list -> list.map {
            it.toDomain()
        } }
    }

    override fun getAvailableLocations(): Flow<List<LocationWithScenesModel>> {
        return locationsDao.getAvailableWithScenes().map { list -> list.map {
            it.toDomain()
        } }
    }

    override suspend fun placeLocationOnMap(
        locationId: String,
        mapId: String,
        x: Float,
        y: Float
    ) {
        locationsDao.placeLocationOnMap(
            locationId = locationId,
            mapId = mapId,
            x = x,
            y = y
        )
    }

    override suspend fun updateLocationSlot(
        locationId: String,
        x: Float,
        y: Float
    ) {
        locationsDao.updateLocationSlot(
            locationId = locationId,
            x = x,
            y = y
        )
    }

}