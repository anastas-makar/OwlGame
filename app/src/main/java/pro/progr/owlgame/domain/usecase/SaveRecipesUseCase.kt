package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.RecipeWithSuppliesModel
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import javax.inject.Inject

class SaveRecipesUseCase @Inject constructor(private val supplyToRecipeRepository: SupplyToRecipeRepository,
                                             private val imageRepository: ImageRepository)  {

    suspend operator fun invoke(recipes: List<RecipeWithSuppliesModel>): List<RecipeWithSuppliesModel> {
        val savedRecipes = recipes.map { recipe ->
            recipe.copy(
                resultSupply = recipe.resultSupply.copy(imageUrl = imageRepository.saveImageLocally(recipe.resultSupply.imageUrl)),
                ingredients = recipe.ingredients.map { ing -> ing.copy(
                    supplyModel = ing.supplyModel.copy(
                        imageUrl = imageRepository.saveImageLocally(ing.supplyModel.imageUrl)
                    )
                ) }

            )
        }

        supplyToRecipeRepository.saveRecipes(savedRecipes)

        return savedRecipes
    }
}