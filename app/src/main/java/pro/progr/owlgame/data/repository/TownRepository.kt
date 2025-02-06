package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pro.progr.owlgame.data.db.SlotsDao
import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.db.MapWithDataDao
import pro.progr.owlgame.data.db.TownsDao
import javax.inject.Inject

class TownRepository @Inject constructor(val townsDao: TownsDao,
    val slotsDao: SlotsDao,
    val mapWithDataDao: MapWithDataDao) {

    fun getTownById(id : Long) : Flow<Town> {
        return MutableStateFlow(Town(id, "Кубинка ${id}", ""))
    }

    fun insertTown(town : Town) : Town {
        val id = townsDao.insert(town)

        return town.copy(id = id)
    }

/*    fun insertSlot(town: Town, slotNum: Int, buildingId : Int): Flow<MapData> {
        slotsDao.insert(Slot(0, slotNum = slotNum, buildingId = buildingId, townId = town.id))

        //todo:
        return mapWithDataDao.getTownWithData(town.id)
    }*/
}
