package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.repository.SlotsRepository
import pro.progr.owlgame.domain.usecase.FoundTownUseCase
import pro.progr.owlgame.presentation.ui.model.BuildingModel
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.ui.model.MapTypeUI
import javax.inject.Inject

class MapViewModel @Inject constructor(
    mapsRepository: MapsRepository,
    private val buildingsRepository: BuildingsRepository,
    private val slotsRepository: SlotsRepository,
    private val expeditionsRepository: ExpeditionsRepository,
    mapId: String,
    private val foundTownUseCase: FoundTownUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val map: StateFlow<MapData> = mapsRepository.getMapById(mapId)
        .flatMapLatest { mapWithData ->
            if (mapWithData == null) {
                flowOf(MapData("", "", "", MapTypeUI.LOADING))
            } else {
                val mapEntity = mapWithData.mapEntity

                val buildingsFlow =
                    if (mapEntity.type == MapType.TOWN) {
                        buildingsRepository.getBuildingsWithAnimals(mapId)
                    } else {
                        flowOf(emptyMap())
                    }

                val expeditionFlow =
                    if (
                        mapEntity.type == MapType.OCCUPIED ||
                        mapEntity.type == MapType.EXPEDITION
                    ) {
                        expeditionsRepository.getExpeditionWithData(mapId)
                    } else {
                        flowOf(null)
                    }

                combine(buildingsFlow, expeditionFlow) { buildingsMap, expeditionWithData ->
                    MapData(
                        id = mapEntity.id,
                        name = mapEntity.name,
                        imageUrl = mapEntity.imagePath,
                        type = mapEntity.type,
                        buildings = buildingsMap.values
                            .toList()
                            .sortedWith(compareBy({ it.building.x }, { it.building.id })),
                        expedition = expeditionWithData
                    )
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MapData("", "", "", MapTypeUI.LOADING)
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
        foundTown.update { _ -> false }
        viewModelScope.launch (Dispatchers.IO) {
            foundTownUseCase(map.id, townName, "Улица Главная")
        }

    }

    fun getAvailableBuildings() : Flow<List<Building>> {
        return buildingsRepository.getAvailableBuildings()
    }

    fun saveSlot(x: Float, y: Float, mapId: String, buildingId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            slotsRepository.saveSlot(x, y, mapId, buildingId)
        }
        newHouseState.value = false
        selectedBuilding.value = null
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