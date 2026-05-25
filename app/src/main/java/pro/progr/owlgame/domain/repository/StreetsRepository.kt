package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.StreetDirection
import pro.progr.owlgame.domain.model.StreetModel

interface StreetsRepository {

    suspend fun createStreet(mapId : String,
                             name : String,
                             direction : StreetDirection
    ) : String

    suspend fun deleteStreet(streetId: String)

    suspend fun moveBuildingToStreet(
        buildingId: String,
        streetId: String?
    )

    fun getStreets(mapId: String) : Flow<List<StreetModel>>
}

