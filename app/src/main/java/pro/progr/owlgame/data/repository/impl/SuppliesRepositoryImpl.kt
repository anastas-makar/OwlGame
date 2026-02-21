package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.repository.SuppliesRepository
import javax.inject.Inject

class SuppliesRepositoryImpl @Inject constructor(
    private val suppliesDao: SuppliesDao
) : SuppliesRepository {

    override suspend fun insert(plants: List<Supply>) {
        suppliesDao.insert(plants)
    }

    override suspend fun updateAmount(supplyId : String, amount : Int) {
        suppliesDao.updateAmount(supplyId, amount)
    }

    override fun observeById(supplyId : String) : Flow<Supply?> {
        return suppliesDao.getById(supplyId)
    }
}