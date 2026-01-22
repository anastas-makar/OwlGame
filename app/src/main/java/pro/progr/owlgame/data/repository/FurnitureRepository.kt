package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Furniture
import pro.progr.owlgame.data.db.FurnitureDao
import javax.inject.Inject

class FurnitureRepository @Inject constructor(
    private val furnitureDao: FurnitureDao
) {

    suspend fun insert(items: List<Furniture>) {
        furnitureDao.insert(items)
    }

    fun observeByRoomId(roomId: String) : Flow<List<Furniture>> {
        return furnitureDao.observeByRoomId(roomId)
    }

    fun getAvailableFurnitureItems() : Flow<List<Furniture>> {
        return furnitureDao.getAvailable()
    }

    fun updatePos(id: String, x: Float, y: Float) {
        furnitureDao.updatePosition(id, x, y)
    }

    fun setFurniture(itemId: String, roomId: String) {
        furnitureDao.setToRoom(itemId, roomId)
    }
}