package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.RecipeWithSuppliesModel
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import javax.inject.Inject

class SaveRecipesUseCase @Inject constructor(private val supplyToRecipeRepository: SupplyToRecipeRepository,
                                             private val imageRepository: ImageRepository)  {

    suspend operator fun invoke(recipes: List<RecipeWithSuppliesModel>): List<RecipeWithSuppliesModel> {
        val savedRecipes = recipes.map { recipe ->
            val resultSupplyImageUrl = imageRepository
                .saveImageLocally(
                    imageUrl = recipe.resultSupply.imageUrl,
                    imageKey = recipe.resultSupply.imageKey)
            recipe.copy(
                resultImageUrl = resultSupplyImageUrl,
                resultSupply = recipe.resultSupply.copy(imageUrl = resultSupplyImageUrl),
                ingredients = recipe.ingredients.map { ing -> ing.copy(
                    supplyModel = ing.supplyModel.copy(
                        imageUrl = imageRepository.saveImageLocally(
                            imageUrl = ing.supplyModel.imageUrl,
                            imageKey = ing.supplyModel.imageKey)
                    )
                ) }

            )
        }

        supplyToRecipeRepository.saveRecipes(savedRecipes)

        return savedRecipes
    }
}
