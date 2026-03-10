package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.ExpeditionWithData

interface ExpeditionsRepository {

    fun getExpeditionWithData(mapId: String) : Flow<ExpeditionWithData>

}

