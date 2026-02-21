package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Furniture
import pro.progr.owlgame.data.db.FurnitureDao
import pro.progr.owlgame.data.repository.FurnitureRepository
import javax.inject.Inject

class FurnitureRepositoryImpl @Inject constructor(
    private val furnitureDao: FurnitureDao
) : FurnitureRepository {

    override suspend fun insert(items: List<Furniture>) {
        furnitureDao.insert(items)
    }

    override fun observeByRoomId(roomId: String) : Flow<List<Furniture>> {
        return furnitureDao.observeByRoomId(roomId)
    }

    override fun getAvailableFurnitureItems() : Flow<List<Furniture>> {
        return furnitureDao.getAvailable()
    }

    override fun updatePos(id: String, x: Float, y: Float) {
        furnitureDao.updatePosition(id, x, y)
    }

    override fun setFurniture(itemId: String, roomId: String) {
        furnitureDao.setToRoom(itemId, roomId)
    }
}