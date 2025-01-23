package pro.progr.owlgame.domain

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.TownWithData
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.Map
import javax.inject.Inject

class SaveMapUseCase @Inject constructor(val mapsRepository: MapsRepository) {
    operator fun invoke(map: Map) : Flow<TownWithData> {
        TODO()
    }
}