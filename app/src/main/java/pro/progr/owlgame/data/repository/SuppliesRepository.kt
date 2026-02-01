package pro.progr.owlgame.data.repository

import javax.inject.Inject
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.db.Supply

class SuppliesRepository @Inject constructor(
    private val suppliesDao: SuppliesDao
) {

    suspend fun insert(plants: List<Supply>) {
        suppliesDao.insert(plants)
    }
}