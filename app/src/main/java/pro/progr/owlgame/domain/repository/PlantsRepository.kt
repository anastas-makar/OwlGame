package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.PlantModel

interface PlantsRepository {

    suspend fun insert(plants: List<PlantModel>)

    suspend fun markDeleted(id: String)

    fun observeByGardenId(gardenId: String) : Flow<List<PlantModel>>

    fun getAvailablePlants() : Flow<List<PlantModel>>

    fun updatePos(id: String, x: Float, y: Float)

    fun setPlant(itemId: String, gardenId: String)

    suspend fun addReadinessToAllPlanted(delta: Float)
}