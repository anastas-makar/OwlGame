package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.model.GardenItemModel
import pro.progr.owlgame.domain.model.GardenType
import pro.progr.owlgame.domain.model.SupplyModel
import pro.progr.owlgame.domain.repository.GardenItemsRepository
import pro.progr.owlgame.domain.repository.SuppliesRepository

class GardenZoneViewModel @Inject constructor(
    private val gardenItemsRepo: GardenItemsRepository,
    private val suppliesRepository: SuppliesRepository,
    private val gardenId: String,
    private val gardenType: GardenType
) : ViewModel() {

    val selectGardenItemsState: MutableState<Boolean> = mutableStateOf(false)
    val gardenItems: StateFlow<List<GardenItemModel>> =
        gardenItemsRepo.observeByGardenId(gardenId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableGardenItems: StateFlow<List<GardenItemModel>> = gardenItemsRepo.getAvailableGardenItems(gardenType)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateGardenItemPos(id: String, x: Float, y: Float) {
        viewModelScope.launch(Dispatchers.IO) { gardenItemsRepo.updatePos(id, x, y) }
    }

    fun setGardenItem(gardenItem: GardenItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            gardenItemsRepo.setGardenItem(gardenItem.id, gardenId)
        }
        selectGardenItemsState.value = false
    }

    fun observeSupply(id: String): Flow<SupplyModel?> = suppliesRepository.observeById(id)

    fun harvestSupply(gardenItem: GardenItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            suppliesRepository.updateAmount(gardenItem.supplyId, gardenItem.supplyAmount)
            gardenItemsRepo.flushReadinessForItem(gardenItem.id)
        }
    }
}