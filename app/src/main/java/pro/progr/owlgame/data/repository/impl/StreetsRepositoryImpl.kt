package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.db.Street
import pro.progr.owlgame.data.db.StreetsDao
import pro.progr.owlgame.data.repository.StreetsRepository
import java.util.UUID
import javax.inject.Inject

class StreetsRepositoryImpl @Inject constructor(
    val streetsDao: StreetsDao
) : StreetsRepository {

    override fun createStreet(mapId : String, name : String) : String {
        val id = UUID.randomUUID().toString()
        streetsDao.insert(Street(id = id,
            name, mapId))

        return id
    }

}

