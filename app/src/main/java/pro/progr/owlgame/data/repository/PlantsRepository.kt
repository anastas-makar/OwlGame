package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Plant

interface PlantsRepository {

    suspend fun insert(plants: List<Plant>)

    suspend fun markDeleted(id: String)

    fun observeByGardenId(gardenId: String) : Flow<List<Plant>>

    fun getAvailablePlants() : Flow<List<Plant>>

    fun updatePos(id: String, x: Float, y: Float)

    fun setPlant(itemId: String, gardenId: String)

    suspend fun addReadinessToAllPlanted(delta: Float)
}