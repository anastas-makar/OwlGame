package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.db.Supply

class SuppliesRepository @Inject constructor(
    private val suppliesDao: SuppliesDao
) {

    suspend fun insert(plants: List<Supply>) {
        suppliesDao.insert(plants)
    }

    suspend fun updateAmount(supplyId : String, amount : Int) {
        suppliesDao.updateAmount(supplyId, amount)
    }

    fun observeById(supplyId : String) : Flow<Supply?> {
        return suppliesDao.getById(supplyId)
    }
}