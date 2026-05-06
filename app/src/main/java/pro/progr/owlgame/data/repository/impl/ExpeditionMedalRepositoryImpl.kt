package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.dao.ExpeditionMedalDao
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.ExpeditionMedalModel
import pro.progr.owlgame.domain.repository.ExpeditionMedalRepository
import javax.inject.Inject

class ExpeditionMedalRepositoryImpl @Inject constructor(private val medalDao: ExpeditionMedalDao) :
    ExpeditionMedalRepository {
    override fun getAnimalMedals(animalId: String): Flow<List<ExpeditionMedalModel>> {
        return medalDao.getAnimalMedals(animalId).map { list ->
            list.map {
                it.toDomain()
            }
        }
    }
}
