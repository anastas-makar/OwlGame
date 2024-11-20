package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.db.TownsDao
import javax.inject.Inject

class TownRepository @Inject constructor(val townsDao: TownsDao) {

    fun getTownById(id : Int) : Flow<Town> {
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

    fun insertTown(town : Town) {
        townsDao.insert(town)
    }
}
