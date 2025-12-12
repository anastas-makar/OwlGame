package pro.progr.owlgame.data.repository

import androidx.room.Transaction
import pro.progr.owlgame.data.db.BuildingsDao
import javax.inject.Inject

class SlotsRepository @Inject constructor(
    private val buildingsDao: BuildingsDao) {

    @Transaction
    fun saveSlot(x : Float, y : Float, mapId : String, buildingId : String) {
        buildingsDao.setToMap(buildingId, mapId, x, y)
    }

    fun updateSlot(buildingId : String, x : Float, y : Float) {
        buildingsDao.updateOnMap(buildingId, x, y)
    }
}