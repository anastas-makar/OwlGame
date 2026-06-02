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
import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.domain.model.MapType
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.model.StreetDirection
import pro.progr.owlgame.domain.model.StreetModel
import pro.progr.owlgame.domain.model.StreetWithBuildingsModel
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.domain.repository.LocationsRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.repository.SlotsRepository
import pro.progr.owlgame.domain.repository.StreetsRepository
import javax.inject.Inject

private const val MAIN_STREET_NAME = "Улица Главная"

private val MAIN_STREET_DIRECTION = StreetDirection.WEST_TO_EAST

class MapViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val buildingsRepository: BuildingsRepository,
    private val streetsRepository: StreetsRepository,
    private val slotsRepository: SlotsRepository,
    private val expeditionsRepository: ExpeditionsRepository,
    private val locationsRepository: LocationsRepository,
    mapId: String
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val map: StateFlow<MapWithDataModel> = mapsRepository.getMapById(mapId)
        .flatMapLatest { mapWithData ->
            if (mapWithData == null) {
                flowOf(MapWithDataModel("", "", "", MapType.LOADING))
            } else {

                val buildingsFlow =
                    if (mapWithData.type == MapType.TOWN) {
                        buildingsRepository.getBuildingsWithAnimals(mapId)
                    } else {
                        flowOf(emptyMap())
                    }

                val expeditionFlow =
                    if (
                        mapWithData.type == MapType.OCCUPIED ||
                        mapWithData.type == MapType.EXPEDITION
                    ) {
                        expeditionsRepository.getExpeditionWithData(mapId)
                    } else {
                        flowOf(null)
                    }

                val streetsFlow =
                    if (mapWithData.type == MapType.TOWN) {
                        streetsRepository.getStreets(mapId)
                    } else {
                        flowOf(emptyList())
                    }

                val locationsFlow =
                    locationsRepository.getLocationsWithScenesByMapId(mapId)

                combine(buildingsFlow, expeditionFlow, streetsFlow,
                    locationsFlow) { buildingsMap, expeditionWithData, streets, locations ->

                    val buildings = buildingsMap.values
                        .toList()
                        .sortedWith(compareBy({ it.x }, { it.id }))

                    val sortedLocations = locations
                        .sortedWith(compareBy({ it.x }, { it.id }))

                    MapWithDataModel(
                        id = mapWithData.id,
                        name = mapWithData.name,
                        imageUrl = mapWithData.imageUrl,
                        type = mapWithData.type,
                        buildings = buildings,
                        streets = buildStreetSections(
                            buildings = buildings,
                            streets = streets
                        ),
                        locations = sortedLocations,
                        expedition = expeditionWithData
                    )
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MapWithDataModel("", "", "", MapType.LOADING)
        )

    private fun buildStreetSections(
        buildings: List<BuildingWithAnimalModel>,
        streets: List<StreetModel>
    ): List<StreetWithBuildingsModel> {
        val buildingsByStreetId = buildings.groupBy { it.streetId }

        val mainBuildings = buildingsByStreetId[null].orEmpty()

        val mainStreet = if (mainBuildings.isNotEmpty()) {
            listOf(
                StreetWithBuildingsModel(
                    id = null,
                    name = MAIN_STREET_NAME,
                    direction = MAIN_STREET_DIRECTION,
                    isMain = true,
                    buildings = sortBuildings(mainBuildings, MAIN_STREET_DIRECTION)
                )
            )
        } else {
            emptyList()
        }

        val userStreets = streets.map { street ->
            val streetBuildings = buildingsByStreetId[street.id].orEmpty()

            StreetWithBuildingsModel(
                id = street.id,
                name = street.name,
                direction = street.direction,
                isMain = false,
                buildings = sortBuildings(streetBuildings, street.direction)
            )
        }

        return mainStreet + userStreets
    }

    private fun sortBuildings(
        buildings: List<BuildingWithAnimalModel>,
        direction: StreetDirection
    ): List<BuildingWithAnimalModel> {
        return when (direction) {
            StreetDirection.WEST_TO_EAST ->
                buildings.sortedWith(compareBy({ it.x }, { it.id }))

            StreetDirection.NORTH_TO_SOUTH ->
                buildings.sortedWith(compareBy({ it.y }, { it.id }))
        }
    }

    val foundTown = MutableStateFlow(false)

    val newHouseState = mutableStateOf(false)

    val selectHouseState = mutableStateOf(false)

    val selectFortressState = mutableStateOf(false)

    val selectedBuilding = mutableStateOf<BuildingModel?>(null)

    val selectLocationState = mutableStateOf(false)

    val newLocationState = mutableStateOf(false)

    val selectedLocation = mutableStateOf<LocationWithScenesModel?>(null)

    fun startToFoundTown() {
        viewModelScope.launch (Dispatchers.Default) {
            foundTown.update { _ -> true }
        }
    }

    fun foundTown(map: MapWithDataModel, townName: String) {
        foundTown.update { _ -> false }
        viewModelScope.launch (Dispatchers.IO) {
            mapsRepository.setTown(
                name = townName,
                mapId = map.id
            )
        }

    }

    fun getAvailableBuildings() : Flow<List<BuildingModel>> {
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

    fun purchase(diamondDao: PurchaseInterface, building: BuildingModel) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                diamondDao.spendDiamonds(building.price)
            }

            if (result.isSuccess) {
                selectHouseState.value = false
                selectFortressState.value = false

                selectedBuilding.value = building
                newHouseState.value = true
            }
        }
    }

    fun resolveExpeditionProgress(expeditionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            expeditionsRepository.resolveExpeditionProgress(expeditionId)
        }
    }

    fun createStreet(
        mapId: String,
        name: String,
        direction: StreetDirection
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            streetsRepository.createStreet(
                mapId = mapId,
                name = name,
                direction = direction
            )
        }
    }

    fun deleteStreet(streetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            streetsRepository.deleteStreet(streetId)
        }
    }

    fun moveBuildingToStreet(buildingId: String, streetId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            streetsRepository.moveBuildingToStreet(
                buildingId = buildingId,
                streetId = streetId
            )
        }
    }

    fun getAvailableLocations(): Flow<List<LocationWithScenesModel>> {
        return locationsRepository.getAvailableLocations()
    }

    fun saveLocationSlot(x: Float, y: Float, mapId: String, locationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            locationsRepository.placeLocationOnMap(
                locationId = locationId,
                mapId = mapId,
                x = x,
                y = y
            )
        }
        newLocationState.value = false
        selectedLocation.value = null
    }

    fun updateLocationSlot(locationId: String, x: Float, y: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            locationsRepository.updateLocationSlot(locationId, x, y)
        }
    }

    fun purchaseLocation(diamondDao: PurchaseInterface, location: LocationWithScenesModel) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                diamondDao.spendDiamonds(location.price)
            }

            if (result.isSuccess) {
                selectLocationState.value = false
                selectedLocation.value = location
                newLocationState.value = true
            }
        }
    }
}