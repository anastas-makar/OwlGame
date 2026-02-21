package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Furniture

interface FurnitureRepository {

    suspend fun insert(items: List<Furniture>)

    fun observeByRoomId(roomId: String) : Flow<List<Furniture>>

    fun getAvailableFurnitureItems() : Flow<List<Furniture>>

    fun updatePos(id: String, x: Float, y: Float)

    fun setFurniture(itemId: String, roomId: String)
}