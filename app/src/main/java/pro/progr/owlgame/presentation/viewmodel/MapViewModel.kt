package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.domain.FoundTownUseCase
import pro.progr.owlgame.presentation.ui.model.BuildingModel
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

    val selectedBuilding = mutableStateOf<BuildingModel?>(null)

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

    fun getAvailableHouses() : Flow<List<Building>> {
        return buildingsRepository.getAvailableHouses()
    }

    fun getBuildingsOnMap() : Flow<List<BuildingModel>> {
        return buildingsRepository.getBuildingsOnMap().map {buildingList ->
            buildingList.map { building ->
                BuildingModel(
                    building.id,
                    building.name,
                    R.drawable.test1
                )
            }
        }
    }

}