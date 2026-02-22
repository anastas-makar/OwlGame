package pro.progr.owlgame.data.repository.impl

import android.util.Log
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
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

            val recipeIds = recipes.map { it.id }
            supplyToRecipeDao.markDeletedByRecipeIds(recipeIds)

            supplyToRecipeDao.upsertAll(
                links.map { it.copy(deleted = false) }
            )
        }

    }

    override suspend fun craftSupplyByRecipe(recipeId: String): CraftResult = db.withTransaction {
        val recipe = recipesDao.getRecipeById(recipeId) ?: return@withTransaction CraftResult.NotFound
        val need = supplyToRecipeDao.getByRecipe(recipeId)

        // какие supply надо прочитать (ингредиенты + результат)
        val ids = (need.map { it.supplyId } + recipe.resSupplyId).distinct()
        val supplies = suppliesDao.getByIds(ids).associateBy { it.id }

        val missingIds = ids.filterNot { it in supplies.keys }
        if (missingIds.isNotEmpty()) {
            Log.e("SupplyToRecipeRepositoryImpl", "Supply ids missing: $missingIds")
            return@withTransaction CraftResult.BrokenRecipe
        }

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

    override fun <T>observeRecipes(mapFun: (recipes: List<Recipe>,
                                            supplies: List<Supply>,
                                            links: List<SupplyToRecipe>
            ) -> List<T>): Flow<List<T>> {
        return combine(
            recipesDao.observeAll(),
            suppliesDao.observeAll(),
            supplyToRecipeDao.observeAll()
        ) { recipes, supplies, links ->
            mapFun(recipes, supplies, links)
        }
    }
}