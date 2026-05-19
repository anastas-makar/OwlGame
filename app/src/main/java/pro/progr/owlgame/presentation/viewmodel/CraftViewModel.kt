package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.model.CraftResult
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import pro.progr.owlgame.domain.model.RecipeModel
import pro.progr.owlgame.domain.model.SupplyModel
import pro.progr.owlgame.domain.repository.SuppliesRepository
import javax.inject.Inject

class CraftViewModel @Inject constructor(
    private val supplyToRecipeRepository: SupplyToRecipeRepository,
    private val supplyRepository: SuppliesRepository,
    animalsRepository: AnimalsRepository,
    animalId: String
) : ViewModel() {

    val animal = animalsRepository.getAnimalById(animalId)

    val recipes: StateFlow<List<RecipeModel>> =
        supplyToRecipeRepository.observeRecipes()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val availableSupplies: StateFlow<List<SupplyModel>> =
        supplyRepository.getAllAvailableSupplies()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _selectedRecipeId = MutableStateFlow<String?>(null)

    val selectedRecipe: StateFlow<RecipeModel?> =
        combine(recipes, _selectedRecipeId) { list, id ->
            id?.let { rid -> list.firstOrNull { it.recipeId == rid } }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _selectedSupplyId = MutableStateFlow<String?>(null)

    val selectedSupply: StateFlow<SupplyModel?> =
        combine(availableSupplies, _selectedSupplyId) { list, id ->
            id?.let { sid -> list.firstOrNull { it.id == sid } }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _toast = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val toast: SharedFlow<String> = _toast

    fun onRecipeClick(id: String) {
        _selectedRecipeId.value = id
    }

    fun dismissRecipeDialog() {
        _selectedRecipeId.value = null
    }

    fun onSupplyClick(id: String) {
        _selectedSupplyId.value = id
    }

    fun dismissSupplyDialog() {
        _selectedSupplyId.value = null
    }

    fun craftSelected() {
        val id = _selectedRecipeId.value ?: return

        viewModelScope.launch {
            when (supplyToRecipeRepository.craftSupplyByRecipe(id)) {
                CraftResult.Success -> _toast.tryEmit("Готово")
                CraftResult.NotEnoughIngredients -> _toast.tryEmit("Не хватает ингредиентов")
                CraftResult.NotFound -> _toast.tryEmit("Рецепт не найден")
                CraftResult.BrokenRecipe -> _toast.tryEmit("Рецепт повреждён")
                else -> {}
            }
        }
    }
}