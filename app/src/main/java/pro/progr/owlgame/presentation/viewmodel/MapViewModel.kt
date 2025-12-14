package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.Building
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

    val map: StateFlow<MapData> = combine(
        mapsRepository.getMapById(mapId),
        buildingsRepository.getBuildingsWithAnimals(mapId)
    ) { mapWithData, buildingsMap ->
        if (mapWithData != null) {
            MapData(
                id = mapWithData.mapEntity.id,
                name = mapWithData.mapEntity.name,
                imageUrl = mapWithData.mapEntity.imagePath,
                town = mapWithData.town,
                buildings = buildingsMap.values.toList()
            )
        } else {
            MapData("", "", "")
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MapData("", "", "")
    )

    val foundTown = MutableStateFlow(false)

    val newHouseState = mutableStateOf(false)

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
            foundTownUseCase(map, townName)
        }

    }

    fun getAvailableBuildings() : Flow<List<Building>> {
        return buildingsRepository.getAvailableBuildings()
    }

    fun saveSlot(x: Float, y: Float, mapId: String, buildingId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            slotsRepository.saveSlot(x, y, mapId, buildingId)
            newHouseState.value = false
            selectedBuilding.value = null
        }
    }

    fun updateSlot(buildingId : String, x : Float, y : Float) {
        viewModelScope.launch (Dispatchers.IO) {
            slotsRepository.updateSlot(buildingId, x, y)
        }
    }

    fun purchase(diamondDao: PurchaseInterface, building: Building) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                diamondDao.spendDiamonds(building.price)
            }

            if (result.isSuccess) {
                selectHouseState.value = false
                selectFortressState.value = false

                selectedBuilding.value = BuildingModel(
                    building.id,
                    building.name,
                    building.imageUrl
                )
                newHouseState.value = true
            }
        }
    }

}