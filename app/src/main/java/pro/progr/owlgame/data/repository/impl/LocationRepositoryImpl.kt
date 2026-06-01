package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.LocationDao
import pro.progr.owlgame.data.db.dao.LocationSceneDao
import pro.progr.owlgame.data.db.entity.Location
import pro.progr.owlgame.data.db.entity.LocationScene
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toEntity
import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    val locationDao: LocationDao,
    val locationSceneDao: LocationSceneDao,
    val database: OwlGameDatabase
) : LocationRepository {
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
            locationDao.insert(locationEntities)
            locationSceneDao.insert(locationSceneEntities)
        }
    }

}