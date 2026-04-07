package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.entity.Supply

interface SuppliesRepository {

    suspend fun insert(plants: List<Supply>)

    suspend fun updateAmount(supplyId : String, amount : Int)

    fun observeById(supplyId : String) : Flow<Supply?>

    fun getAllSupplies(): Flow<List<Supply>>

    fun getAllAvailableSupplies(): Flow<List<Supply>>
}