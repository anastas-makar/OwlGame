package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Supply

interface SuppliesRepository {

    suspend fun insert(plants: List<Supply>)

    suspend fun updateAmount(supplyId : String, amount : Int)

    fun observeById(supplyId : String) : Flow<Supply?>
}