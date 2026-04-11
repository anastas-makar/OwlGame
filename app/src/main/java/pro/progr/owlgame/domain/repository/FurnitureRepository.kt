package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.FurnitureModel

interface FurnitureRepository {

    suspend fun insert(items: List<FurnitureModel>)

    fun observeByRoomId(roomId: String) : Flow<List<FurnitureModel>>

    fun getAvailableFurnitureItems() : Flow<List<FurnitureModel>>

    fun updatePos(id: String, x: Float, y: Float)

    fun setFurniture(itemId: String, roomId: String)
}