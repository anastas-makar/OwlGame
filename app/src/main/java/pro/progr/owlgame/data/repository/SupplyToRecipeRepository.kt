package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.Recipe
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.db.SupplyToRecipe

interface SupplyToRecipeRepository {
    suspend fun saveRecipes(
        supplies: List<Supply>,
        recipes: List<Recipe>,
        links: List<SupplyToRecipe>
    )
}