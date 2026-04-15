package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.dao.SuppliesDao
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.SupplyModel
import pro.progr.owlgame.domain.repository.SuppliesRepository
import javax.inject.Inject
import kotlin.collections.map

class SuppliesRepositoryImpl @Inject constructor(
    private val suppliesDao: SuppliesDao
) : SuppliesRepository {

    override suspend fun insert(supplies: List<SupplyModel>) {
        suppliesDao.insert(supplies.map { s -> s.toData() })
    }

    override suspend fun updateAmount(supplyId : String, amount : Int) {
        suppliesDao.updateAmount(supplyId, amount)
    }

    override fun observeById(supplyId : String) : Flow<SupplyModel?> {
        return suppliesDao.getById(supplyId).map {
                supply ->  supply?.toDomain() }
    }

    override fun getAllSupplies(): Flow<List<SupplyModel>> {
        return suppliesDao.observeAll().map { list -> list.map {
                supply ->  supply.toDomain() } }
    }

    override fun getAllAvailableSupplies(): Flow<List<SupplyModel>> {
        return suppliesDao.observeAllAvailable().map { list -> list.map {
            supply ->  supply.toDomain() } }
    }
}