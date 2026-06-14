package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.FurnitureRepository
import pro.progr.owlgame.domain.repository.GardenItemsRepository
import pro.progr.owlgame.domain.repository.LocationsRepository
import pro.progr.owlgame.domain.repository.PlantsRepository
import pro.progr.owlgame.domain.repository.SuppliesRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import pro.progr.owlgame.presentation.viewmodel.InventoryViewModel
import javax.inject.Inject

class InventoryViewModelFactory @Inject constructor(
    private val buildingsRepository: BuildingsRepository,
    private val locationsRepository: LocationsRepository,
    private val furnitureRepository: FurnitureRepository,
    private val plantsRepository: PlantsRepository,
    private val gardenItemsRepository: GardenItemsRepository,
    private val supplyToRecipeRepository: SupplyToRecipeRepository,
    private val suppliesRepository: SuppliesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            return InventoryViewModel(
                buildingsRepository,
                locationsRepository,
                furnitureRepository,
                plantsRepository,
                gardenItemsRepository,
                supplyToRecipeRepository,
                suppliesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}