package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.GardenItemsDao
import javax.inject.Inject

class GardenItemsRepository @Inject constructor(
    private val gardenItemsDao: GardenItemsDao
) {
    suspend fun insert(gardenItems: List<GardenItem>) {
        gardenItemsDao.insert(gardenItems)
    }
}