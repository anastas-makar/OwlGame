package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.GardenType
import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.repository.GardenItemsRepository
import pro.progr.owlgame.data.repository.PlantsRepository

class GardenZoneViewModel @Inject constructor(
    private val gardenItemsRepo: GardenItemsRepository,
    private val plantsRepo: PlantsRepository,
    private val gardenId: String,
    private val gardenType: GardenType
) : ViewModel() {

    val selectGardenItemsState: MutableState<Boolean> = mutableStateOf(false)
    val selectPlantState: MutableState<Boolean> = mutableStateOf(false)
    val gardenItems: StateFlow<List<GardenItem>> =
        gardenItemsRepo.observeByGardenId(gardenId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val plants: StateFlow<List<Plant>> =
        plantsRepo.observeByGardenId(gardenId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateGardenItemPos(id: String, x: Float, y: Float) {
        viewModelScope.launch(Dispatchers.IO) { gardenItemsRepo.updatePos(id, x, y) }
    }

    fun updatePlantPos(id: String, x: Float, y: Float) {
        viewModelScope.launch(Dispatchers.IO) { plantsRepo.updatePos(id, x, y) }
    }

    fun getAvailablePlants() : Flow<List<Plant>> {
        return plantsRepo.getAvailablePlants()
    }

    fun setPlant(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            plantsRepo.setPlant(plant.id, gardenId)
        }
        selectGardenItemsState.value = false
    }

    fun getAvailableGardenItems() : Flow<List<GardenItem>> {
        return gardenItemsRepo.getAvailableGardenItems(gardenType)
    }

    fun setGardenItem(gardenItem: GardenItem) {
        viewModelScope.launch(Dispatchers.IO) {
            gardenItemsRepo.setGardenItem(gardenItem.id, gardenId)
        }
        selectGardenItemsState.value = false
    }
}