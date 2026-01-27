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
import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.repository.PlantsRepository

class KitchenGardenViewModel @Inject constructor(
    private val plantsRepo: PlantsRepository,
    private val gardenId: String
) : ViewModel() {

    val selectPlantState: MutableState<Boolean> = mutableStateOf(false)

    val plants: StateFlow<List<Plant>> =
        plantsRepo.observeByGardenId(gardenId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availablePlants = plantsRepo.getAvailablePlants()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
        selectPlantState.value = false
    }
}