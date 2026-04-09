package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.PouchesRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import pro.progr.owlgame.domain.usecase.SaveFurnitureUseCase
import pro.progr.owlgame.domain.usecase.SaveGardenItemsUseCase
import pro.progr.owlgame.domain.usecase.SaveMapsUseCase
import pro.progr.owlgame.domain.usecase.SavePlantsUseCase
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel
import pro.progr.owlgame.presentation.viewmodel.PouchesViewModel
import javax.inject.Inject

class PouchViewModelFactory @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val saveMapsUseCase: SaveMapsUseCase,
    private val buildingsRepository: BuildingsRepository,
    private val savePlantsUseCase: SavePlantsUseCase,
    private val saveGardenItemsUseCase: SaveGardenItemsUseCase,
    private val saveFurnitureUseCase: SaveFurnitureUseCase,
    private val supplyToRecipeRepository: SupplyToRecipeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PouchesViewModel::class.java)) {
            return PouchesViewModel(pouchesRepository) as T
        } else if (modelClass.isAssignableFrom(InPouchViewModel::class.java)) {
            return InPouchViewModel(pouchesRepository,
                saveMapsUseCase,
                buildingsRepository,
                savePlantsUseCase,
                saveGardenItemsUseCase,
                saveFurnitureUseCase,
                supplyToRecipeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}