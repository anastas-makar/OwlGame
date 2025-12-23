package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.Street
import pro.progr.owlgame.data.db.StreetsDao
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StreetsRepository @Inject constructor(
    val streetsDao: StreetsDao
) {

    fun createStreet(mapId : String, name : String) : String {
        val id = UUID.randomUUID().toString()
        streetsDao.insert(Street(id = id,
            name, mapId))

        return id
    }

}

