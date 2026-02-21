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
import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.repository.PlantsRepository
import pro.progr.owlgame.data.repository.SuppliesRepository
import java.util.UUID

class KitchenGardenViewModel @Inject constructor(
    private val plantsRepo: PlantsRepository,
    private val suppliesRepo: SuppliesRepository,
    private val gardenId: String
) : ViewModel() {

    val selectPlantState: MutableState<Boolean> = mutableStateOf(false)

    val plants: StateFlow<List<Plant>> =
        plantsRepo.observeByGardenId(gardenId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availablePlants: StateFlow<List<Plant>> = plantsRepo.getAvailablePlants()
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


    fun observeSupply(id: String): Flow<Supply?> = suppliesRepo.observeById(id)

    fun harvestSeeds(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            val seeds = List(plant.seedAmount.coerceAtLeast(0)) {
                plant.copy(
                    id = UUID.randomUUID().toString(),
                    gardenId = null,      // семена не посажены
                    x = 0f,
                    y = 0f,
                    readiness = 0f,
                    deleted = false
                )
            }
            if (seeds.isNotEmpty()) plantsRepo.insert(seeds)
            plantsRepo.markDeleted(plant.id)
        }
    }

    fun harvestSupply(plant: Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            suppliesRepo.updateAmount(plant.supplyId, plant.supplyAmount)
            plantsRepo.markDeleted(plant.id)
        }
    }
}