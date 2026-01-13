package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.GardenItemsDao
import javax.inject.Inject

class GardenItemsRepository @Inject constructor(
    private val gardenItemsDao: GardenItemsDao
) {
    suspend fun insert(gardenItems: List<GardenItem>) {
        gardenItemsDao.insert(gardenItems)
    }

    fun observeByGardenId(gardenId: String) : Flow<List<GardenItem>> {
        TODO("Not yet implemented")
    }

    fun updatePos(id: String, x: Float, y: Float) {
        TODO("Not yet implemented")
    }
}