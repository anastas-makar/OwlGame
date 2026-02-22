package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Recipe
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.db.SupplyToRecipe
import pro.progr.owlgame.data.model.CraftResult

interface SupplyToRecipeRepository {
    suspend fun saveRecipes(
        supplies: List<Supply>,
        recipes: List<Recipe>,
        links: List<SupplyToRecipe>
    )

    suspend fun craftSupplyByRecipe(recipeId: String): CraftResult

    fun <T>observeRecipes(mapFun: (recipes: List<Recipe>,
                                   supplies: List<Supply>,
                                   links: List<SupplyToRecipe>) -> List<T>): Flow<List<T>>
}