package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.ExpeditionWithData
import pro.progr.owlgame.data.db.ExpeditionWithDataDao
import pro.progr.owlgame.data.repository.ExpeditionsRepository
import javax.inject.Inject

class ExpeditionsRepositoryImpl @Inject constructor(
    private val expeditionWithDataDao: ExpeditionWithDataDao
) : ExpeditionsRepository {

    override fun getExpeditionWithData(mapId: String) : Flow<ExpeditionWithData> {
        return expeditionWithDataDao.getExpeditionWithData(mapId)
    }

}

