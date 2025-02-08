package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.Slot
import pro.progr.owlgame.data.db.SlotsDao
import javax.inject.Inject

class SlotsRepository @Inject constructor(private val slotsDao: SlotsDao) {

    fun saveSlot(x : Float, y : Float, mapId : String, buildingId : Int) {
        slotsDao.insert(
            Slot(
                x = x,
                y = y,
                mapId = mapId,
                buildingId = buildingId
            )
        )
    }

    fun updateSlot(slot: Slot) {
        slotsDao.update(slot)
    }
}