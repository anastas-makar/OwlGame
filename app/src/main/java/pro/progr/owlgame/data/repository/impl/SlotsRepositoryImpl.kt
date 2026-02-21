package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.db.BuildingsDao
import pro.progr.owlgame.data.repository.SlotsRepository
import javax.inject.Inject

class SlotsRepositoryImpl @Inject constructor(
    private val buildingsDao: BuildingsDao) : SlotsRepository {

    override fun saveSlot(x : Float, y : Float, mapId : String, buildingId : String) {
        buildingsDao.setToMap(buildingId, mapId, x, y)
    }

    override fun updateSlot(buildingId : String, x : Float, y : Float) {
        buildingsDao.updateOnMap(buildingId, x, y)
    }
}