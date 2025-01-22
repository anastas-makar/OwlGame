package pro.progr.owlgame.domain

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.TownWithData
import pro.progr.owlgame.data.web.Map

class SaveMapUseCase {
    operator fun invoke(map: Map) : Flow<TownWithData> {
        TODO()
    }
}