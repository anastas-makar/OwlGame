package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.LocationWithScenesModel

interface LocationRepository {
    suspend fun insert(locations: List<LocationWithScenesModel>)
}