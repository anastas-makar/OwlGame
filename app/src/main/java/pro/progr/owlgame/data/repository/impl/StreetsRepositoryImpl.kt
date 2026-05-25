package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.BuildingsDao
import pro.progr.owlgame.data.db.entity.Street
import pro.progr.owlgame.data.db.dao.StreetsDao
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.StreetDirection
import pro.progr.owlgame.domain.model.StreetModel
import pro.progr.owlgame.domain.repository.StreetsRepository
import java.util.UUID
import javax.inject.Inject
import kotlin.collections.map

class StreetsRepositoryImpl @Inject constructor(
    val streetsDao: StreetsDao,
    val buildingsDao: BuildingsDao,
    val database: OwlGameDatabase
) : StreetsRepository {

    override suspend fun createStreet(mapId : String,
                                      name : String,
                                      direction : StreetDirection) : String {
        val id = UUID.randomUUID().toString()
        streetsDao.insert(Street(id = id,
            mapId = mapId,
            name = name,
            direction = direction.toData()))

        return id
    }

    override suspend fun deleteStreet(streetId: String) {
        database.withTransaction {
            buildingsDao.clearStreetForBuildings(streetId)
            streetsDao.deleteById(streetId)
        }
    }

    override suspend fun moveBuildingToStreet(
        buildingId: String,
        streetId: String?
    ) {
        buildingsDao.updateStreetId(
            buildingId = buildingId,
            streetId = streetId
        )
    }

    override fun getStreets(mapId: String): Flow<List<StreetModel>> {
        return streetsDao.getByMapId(mapId).map { list -> list.map {
            it.toDomain()
        } }
    }

}

