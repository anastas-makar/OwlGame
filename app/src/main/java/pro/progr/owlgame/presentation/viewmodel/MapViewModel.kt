package pro.progr.owlgame.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.Slot
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.SlotsRepository
import pro.progr.owlgame.domain.FoundTownUseCase
import pro.progr.owlgame.presentation.ui.model.BuildingModel
import pro.progr.owlgame.presentation.ui.model.MapData
import javax.inject.Inject

class MapViewModel @Inject constructor(
    mapsRepository: MapsRepository,
    private val buildingsRepository: BuildingsRepository,
    private val slotsRepository: SlotsRepository,
    mapId: String,
    private val foundTownUseCase: FoundTownUseCase,
) : ViewModel() {

    val map : Flow<MapData> = mapsRepository.getMapById(mapId).map { mapWithData ->
        if (mapWithData != null)  {
            Log.wtf("SLOTS: ", mapWithData.slots.toString())
            MapData(
                id = mapWithData.mapEntity.id,
                name = mapWithData.mapEntity.name,
                imageUrl = mapWithData.mapEntity.imagePath,
                town = mapWithData.town,
                slots = mapWithData.slots
            )
        } else {
            MapData("", "", "")
        }
    }

    val foundTown = MutableStateFlow(false)

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

    fun foundTown(map: MapData, townName: String) {
        viewModelScope.launch (Dispatchers.Default) {
            foundTown.update { _ -> false }
            foundTownUseCase.invoke(map, townName)
        }

    }

    fun getAvailableHouses() : Flow<List<Building>> {
        return buildingsRepository.getAvailableHouses()
    }

    fun getBuildingsOnMap() : Flow<List<Building>> {
        return buildingsRepository.getBuildingsOnMap()
    }

    fun saveSlot(x: Float, y: Float, mapId: String, buildingId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            slotsRepository.saveSlot(x, y, mapId, buildingId)
            newHouseState.value = false
            selectedBuilding.value = null
        }
    }

    fun updateSlot(slot: Slot) {
        viewModelScope.launch (Dispatchers.IO) {
            slotsRepository.updateSlot(slot)
        }
    }

}