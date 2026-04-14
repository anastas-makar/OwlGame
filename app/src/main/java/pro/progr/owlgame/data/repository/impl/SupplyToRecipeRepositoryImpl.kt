package pro.progr.owlgame.data.repository.impl

import android.util.Log
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.RecipesDao
import pro.progr.owlgame.data.db.dao.SuppliesDao
import pro.progr.owlgame.data.db.dao.SupplyToRecipeDao
import pro.progr.owlgame.data.db.entity.Recipe
import pro.progr.owlgame.data.db.entity.Supply
import pro.progr.owlgame.data.db.entity.SupplyToRecipe
import pro.progr.owlgame.data.mapper.linkId
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.domain.model.CraftResult
import pro.progr.owlgame.domain.model.IngredientModel
import pro.progr.owlgame.domain.model.RecipeModel
import pro.progr.owlgame.domain.model.RecipeWithSuppliesModel
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import javax.inject.Inject

class SupplyToRecipeRepositoryImpl @Inject constructor(
    private val db: OwlGameDatabase,
    private val suppliesDao: SuppliesDao,
    private val recipesDao: RecipesDao,
    private val supplyToRecipeDao: SupplyToRecipeDao,
    private val imageRepository: ImageRepository
) : SupplyToRecipeRepository {

    override suspend fun saveRecipes(
        recipes: List<RecipeWithSuppliesModel>
    ) {
        if (recipes.isEmpty()) return

        val supplyEntities: List<Supply> =
            buildList {
                recipes.forEach { r ->
                    add(r.resultSupply
                        .copy(imageUrl = imageRepository.saveImageLocally(r.resultSupply.imageUrl))
                        .toData())
                    r.ingredients.forEach { ing -> add(ing.supplyModel
                        .toData()
                        .copy(imageUrl = ing.supplyModel.imageUrl)
                    ) }
                }
            }
                .distinctBy { it.id }

        val recipeEntities = recipes.map { recipeWithSuppliesModel ->
            Recipe(
                id = recipeWithSuppliesModel.recipeId,
                resSupplyId = recipeWithSuppliesModel.resultSupply.id,
                description = recipeWithSuppliesModel.description
            )
        }

        val links: List<SupplyToRecipe> =
            recipes.flatMap { r ->
                // на всякий случай: если один и тот же supply попался несколько раз — суммируем
                r.ingredients
                    .groupBy { it.supplyModel.id }
                    .map { (supplyId, items) ->
                        SupplyToRecipe(
                            id = linkId(r.recipeId, supplyId),
                            supplyId = supplyId,
                            recipeId = r.recipeId,
                            amount = items.sumOf { it.amount }
                        )
                    }
            }

        db.withTransaction {
            suppliesDao.insert(supplyEntities)
            recipesDao.upsertAll(recipeEntities)

            val recipeIds = recipes.map { it.recipeId }
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

    override fun observeRecipes(): Flow<List<RecipeModel>> {
        return combine(
            recipesDao.observeAll(),
            suppliesDao.observeAll(),
            supplyToRecipeDao.observeAll()
        ) { recipes, supplies, links ->
            val suppliesById = supplies.associateBy { it.id }
            val linksByRecipe = links.groupBy { it.recipeId }

            recipes.mapNotNull { recipe ->
                val result = suppliesById[recipe.resSupplyId] ?: return@mapNotNull null

                val ingLinks = linksByRecipe[recipe.id].orEmpty()

                val ingredients = ingLinks.mapNotNull { ingredientLink ->
                    val supply = suppliesById[ingredientLink.supplyId] ?: return@mapNotNull null

                    IngredientModel(
                        supplyId = supply.id,
                        name = supply.name,
                        imageUrl = supply.imageUrl,
                        required = ingredientLink.amount,
                        have = supply.amount
                    )
                }

                RecipeModel(
                    recipeId = recipe.id,
                    resultSupplyId = result.id,
                    resultName = result.name,
                    resultImageUrl = result.imageUrl,
                    description = recipe.description,
                    ingredients = ingredients,
                    craftable = ingredients.all { it.enough })
            }
        }
    }
}