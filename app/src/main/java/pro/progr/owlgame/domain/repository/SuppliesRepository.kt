package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.SupplyModel

interface SuppliesRepository {

    suspend fun insert(supplies: List<SupplyModel>)

    suspend fun updateAmount(supplyId : String, amount : Int)

    fun observeById(supplyId : String) : Flow<SupplyModel?>

    fun getAllSupplies(): Flow<List<SupplyModel>>

    fun getAllAvailableSupplies(): Flow<List<SupplyModel>>
}