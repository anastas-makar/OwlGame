package pro.progr.owlgame.data.model

sealed interface CraftResult {
    data object Success : CraftResult
    data object BrokenRecipe : CraftResult
    data object NotEnoughIngredients : CraftResult
    data object NotFound : CraftResult
}