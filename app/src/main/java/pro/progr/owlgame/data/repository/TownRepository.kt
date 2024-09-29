package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pro.progr.owlgame.data.db.Town
import javax.inject.Inject

class TownRepository @Inject constructor() {

    fun getTownById(id : String) : Flow<Town> {
        return MutableStateFlow(Town(id, "Кубинка ${id}"))
    }

    fun getName() : String {
        return "test"
    }
}
