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
import pro.progr.owlgame.data.model.CraftResult
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.SupplyToRecipeRepository
import pro.progr.owlgame.domain.ObserveRecipesUseCase
import pro.progr.owlgame.domain.model.RecipeModel
import javax.inject.Inject

class CraftViewModel @Inject constructor(
    observeRecipesUseCase: ObserveRecipesUseCase,
    private val supplyToRecipeRepository: SupplyToRecipeRepository,
    animalsRepository: AnimalsRepository,
    animalId: String
) : ViewModel() {

    val animal = animalsRepository.getAnimalById(animalId)

    val recipes: StateFlow<List<RecipeModel>> =
        observeRecipesUseCase()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _selectedId = MutableStateFlow<String?>(null)
    val selectedRecipe: StateFlow<RecipeModel?> =
        combine(recipes, _selectedId) { list, id ->
            id?.let { rid -> list.firstOrNull { it.recipeId == rid } }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _toast = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val toast: SharedFlow<String> = _toast

    fun onRecipeClick(id: String) {
        _selectedId.value = id
    }

    fun dismissDialog() {
        _selectedId.value = null
    }

    fun craftSelected() {
        val id = _selectedId.value ?: return
        viewModelScope.launch {
            when (supplyToRecipeRepository.craftSupplyByRecipe(id)) {
                CraftResult.Success -> _toast.tryEmit("Готово")
                CraftResult.NotEnoughIngredients -> _toast.tryEmit("Не хватает ингредиентов")
                CraftResult.NotFound -> _toast.tryEmit("Рецепт не найден")
                else -> {}
            }
        }
    }
}