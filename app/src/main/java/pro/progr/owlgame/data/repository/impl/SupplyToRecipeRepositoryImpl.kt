package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.Recipe
import pro.progr.owlgame.data.db.RecipesDao
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.db.SupplyToRecipe
import pro.progr.owlgame.data.db.SupplyToRecipeDao
import pro.progr.owlgame.data.model.CraftResult
import pro.progr.owlgame.data.repository.SupplyToRecipeRepository
import javax.inject.Inject

class SupplyToRecipeRepositoryImpl @Inject constructor(
    private val db: OwlGameDatabase,
    private val suppliesDao: SuppliesDao,
    private val recipesDao: RecipesDao,
    private val supplyToRecipeDao: SupplyToRecipeDao
) : SupplyToRecipeRepository {
    override suspend fun saveRecipes(
        supplies: List<Supply>,
        recipes: List<Recipe>,
        links: List<SupplyToRecipe>
    ) {

        db.withTransaction {

            suppliesDao.insert(supplies)

            recipesDao.upsertAll(recipes)

            supplyToRecipeDao.deleteByRecipeIds(recipes.map { it.id })

            supplyToRecipeDao.upsertAll(links)
        }

    }

    override suspend fun craftSupplyByRecipe(recipeId: String): CraftResult = db.withTransaction {
        val recipe = recipesDao.getRecipeById(recipeId) ?: return@withTransaction CraftResult.NotFound
        val need = supplyToRecipeDao.getByRecipe(recipeId)

        // какие supply надо прочитать (ингредиенты + результат)
        val ids = (need.map { it.supplyId } + recipe.resSupplyId).distinct()
        val supplies = suppliesDao.getByIds(ids).associateBy { it.id }

        // проверка наличия ингредиентов
        val enough = need.all { link ->
            val have = supplies[link.supplyId]?.amount ?: 0
            have >= link.amount
        }
        if (!enough) return@withTransaction CraftResult.NotEnoughIngredients

        // списываем ингредиенты
        need.forEach { link ->
            suppliesDao.updateAmount(link.supplyId, -link.amount)
        }

        // добавляем результат (+1 штука за крафт)
        suppliesDao.updateAmount(recipe.resSupplyId, +1)

        CraftResult.Success
    }
}