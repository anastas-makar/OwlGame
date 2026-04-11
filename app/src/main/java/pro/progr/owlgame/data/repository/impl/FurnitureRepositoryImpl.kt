package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.dao.FurnitureDao
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.repository.FurnitureRepository
import javax.inject.Inject

class FurnitureRepositoryImpl @Inject constructor(
    private val furnitureDao: FurnitureDao
) : FurnitureRepository {

    override suspend fun insert(items: List<FurnitureModel>) {
        furnitureDao.insert(items.map { it.toData() })
    }

    override fun observeByRoomId(roomId: String) : Flow<List<FurnitureModel>> {
        return furnitureDao.observeByRoomId(roomId).map { list -> list.map { f -> f.toDomain() }}
    }

    override fun getAvailableFurnitureItems() : Flow<List<FurnitureModel>> {
        return furnitureDao.getAvailable().map { list -> list.map { f -> f.toDomain() }}
        }

    override fun updatePos(id: String, x: Float, y: Float) {
        furnitureDao.updatePosition(id, x, y)
    }

    override fun setFurniture(itemId: String, roomId: String) {
        furnitureDao.setToRoom(itemId, roomId)
    }
}