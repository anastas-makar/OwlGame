package pro.progr.owlgame.data.repository

import androidx.room.Transaction
import pro.progr.owlgame.data.db.BuildingsDao
import pro.progr.owlgame.data.db.Slot
import pro.progr.owlgame.data.db.SlotsDao
import javax.inject.Inject

class SlotsRepository @Inject constructor(
    private val slotsDao: SlotsDao,
    private val buildingsDao: BuildingsDao) {

    @Transaction
    fun saveSlot(x : Float, y : Float, mapId : String, buildingId : String) {
        slotsDao.insert(
            Slot(
                x = x,
                y = y,
                mapId = mapId,
                buildingId = buildingId
            )
        )
        buildingsDao.updateMapId(buildingId, mapId)
    }

    fun updateSlot(slot: Slot) {
        slotsDao.update(slot)
    }
}