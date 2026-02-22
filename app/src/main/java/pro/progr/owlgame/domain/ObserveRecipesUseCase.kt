package pro.progr.owlgame.domain

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.repository.SupplyToRecipeRepository
import pro.progr.owlgame.domain.model.IngredientModel
import pro.progr.owlgame.domain.model.RecipeModel
import javax.inject.Inject

class ObserveRecipesUseCase @Inject constructor(
    private val supplyToRecipeRepository: SupplyToRecipeRepository
) {
     operator fun invoke(): Flow<List<RecipeModel>> {
        return supplyToRecipeRepository.observeRecipes { recipes, supplies, links ->
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