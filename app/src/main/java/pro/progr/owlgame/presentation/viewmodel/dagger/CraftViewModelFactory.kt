package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.SupplyToRecipeRepository
import pro.progr.owlgame.domain.ObserveRecipesUseCase
import pro.progr.owlgame.presentation.viewmodel.CraftViewModel
import javax.inject.Inject

class CraftViewModelFactory @Inject constructor(
    private val observeRecipesUseCase: ObserveRecipesUseCase,
    private val supplyToRecipeRepository: SupplyToRecipeRepository,
    private val animalsRepository: AnimalsRepository
) : ViewModelProvider.Factory {
    var animalId : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CraftViewModel::class.java)) {
            return CraftViewModel(
                observeRecipesUseCase,
                supplyToRecipeRepository,
                animalsRepository,
                animalId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
