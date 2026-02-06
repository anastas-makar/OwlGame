package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.GardenType
import pro.progr.owlgame.data.repository.GardenItemsRepository
import pro.progr.owlgame.data.repository.SuppliesRepository

class GardenZoneViewModel @Inject constructor(
    private val gardenItemsRepo: GardenItemsRepository,
    private val suppliesRepository: SuppliesRepository,
    private val gardenId: String,
    private val gardenType: GardenType
) : ViewModel() {

    val selectGardenItemsState: MutableState<Boolean> = mutableStateOf(false)
    val gardenItems: StateFlow<List<GardenItem>> =
        gardenItemsRepo.observeByGardenId(gardenId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableGardenItems: StateFlow<List<GardenItem>> = gardenItemsRepo.getAvailableGardenItems(gardenType)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateGardenItemPos(id: String, x: Float, y: Float) {
        viewModelScope.launch(Dispatchers.IO) { gardenItemsRepo.updatePos(id, x, y) }
    }

    fun setGardenItem(gardenItem: GardenItem) {
        viewModelScope.launch(Dispatchers.IO) {
            gardenItemsRepo.setGardenItem(gardenItem.id, gardenId)
        }
        selectGardenItemsState.value = false
    }
}