package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.data.db.entity.Supply
import pro.progr.owlgame.data.db.entity.SupplyToRecipe
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import pro.progr.owlgame.data.web.inpouch.RecipeInPouch
import pro.progr.owlgame.data.mapper.linkId
import pro.progr.owlgame.data.mapper.toEntity
import javax.inject.Inject

class SaveRecipesUseCase @Inject constructor(
    private val supplyToRecipeRepository: SupplyToRecipeRepository,
    private val imageRepository: ImageRepository
) {

    suspend operator fun invoke(recipes: List<RecipeInPouch>) {
        if (recipes.isEmpty()) return

        val allSupplies: List<Supply> =
            buildList {
                recipes.forEach { r ->
                    add(r.resultSupply
                        .copy(imageUrl = imageRepository.saveImageLocally(r.resultSupply.imageUrl))
                        .toEntity())
                    r.ingredients.forEach { ing -> add(ing.supplyInPouch
                        .toEntity()
                        .copy(imageUrl = ing.supplyInPouch.imageUrl)
                        ) }
                }
            }
                .distinctBy { it.id }

        val recipeEntities = recipes.map { it.toEntity() }

        val links: List<SupplyToRecipe> =
            recipes.flatMap { r ->
                // на всякий случай: если один и тот же supply попался несколько раз — суммируем
                r.ingredients
                    .groupBy { it.supplyInPouch.id }
                    .map { (supplyId, items) ->
                        SupplyToRecipe(
                            id = linkId(r.id, supplyId),
                            supplyId = supplyId,
                            recipeId = r.id,
                            amount = items.sumOf { it.amount }
                        )
                    }
            }

        supplyToRecipeRepository.saveRecipes(allSupplies, recipeEntities, links)
    }
}