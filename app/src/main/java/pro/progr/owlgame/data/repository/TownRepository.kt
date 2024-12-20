package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pro.progr.owlgame.data.db.Slot
import pro.progr.owlgame.data.db.SlotsDao
import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.db.TownWithData
import pro.progr.owlgame.data.db.TownWithDataDao
import pro.progr.owlgame.data.db.TownsDao
import javax.inject.Inject

class TownRepository @Inject constructor(val townsDao: TownsDao,
    val slotsDao: SlotsDao,
    val townWithDataDao: TownWithDataDao) {

    fun getTownById(id : Long) : Flow<Town> {
        return MutableStateFlow(Town(id, "Кубинка ${id}", ""))
    }

    fun getTownsList() : Flow<List<Town>> {
        return MutableStateFlow(
            listOf(
                Town(1, "Кубинка 1", ""),
                Town(2, "Кубинка 2", ""),
                Town(3, "Кубинка 3", ""),
            )
        )
    }

    fun insertTown(town : Town) : Town {
        val id = townsDao.insert(town)

        return town.copy(id = id)
    }

    fun insertSlot(town: Town, slotNum: Int, buildingId : Int): Flow<TownWithData> {
        slotsDao.insert(Slot(0, slotNum = slotNum, buildingId = buildingId, townId = town.id))

        //todo:
        return townWithDataDao.getTownWithData(town.id)
    }
}
