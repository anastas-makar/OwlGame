package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.CraftResult
import pro.progr.owlgame.domain.model.RecipeModel
import pro.progr.owlgame.domain.model.RecipeWithSuppliesModel

interface SupplyToRecipeRepository {
    suspend fun saveRecipes(
        recipes: List<RecipeWithSuppliesModel>
    )

    suspend fun craftSupplyByRecipe(recipeId: String): CraftResult

    fun observeRecipes(): Flow<List<RecipeModel>>
}
