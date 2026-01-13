package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.repository.GardenItemsRepository
import pro.progr.owlgame.data.repository.PlantsRepository

class GardenZoneViewModel @Inject constructor(
    private val gardenItemsRepo: GardenItemsRepository,
    private val plantsRepo: PlantsRepository,
    private val gardenId: String, // или buildingId, смотри по связям
) : ViewModel() {

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
}