package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.domain.FoundTownUseCase
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val buildingsRepository: BuildingsRepository,
    private val mapId: String,
    private val foundTownUseCase: FoundTownUseCase
) : ViewModel() {

    var map : Flow<Map> = mapsRepository.getMapById(mapId)

    val foundTown = MutableStateFlow(false)

    val townState = mutableStateOf<Town?>(null)

    val newHouseState = mutableStateOf(false)

    val newFortressState = mutableStateOf(false)

    val selectHouseState = mutableStateOf(false)

    val selectFortressState = mutableStateOf(false)

    fun startToFoundTown() {
        viewModelScope.launch (Dispatchers.Default) {
            foundTown.update { _ -> true }
        }
    }

    fun foundTown(map: Map, townName: String) {
        viewModelScope.launch (Dispatchers.Default) {
            foundTown.update { _ -> false }
            townState.value = foundTownUseCase.invoke(map, townName)
        }

    }

    fun getAvailableBuildings() : Flow<List<Building>> {
        //todo:
        return flowOf(listOf(Building(1, "какое-то", "https://progr.pro//api//owl//maps//img//f250800d-6224-4e1c-a6ea-472c1796bb8b.webp")))
    }

}