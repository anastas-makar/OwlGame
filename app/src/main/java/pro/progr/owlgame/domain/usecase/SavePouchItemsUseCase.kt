package pro.progr.owlgame.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.progr.owlgame.domain.model.PouchItemsModel
import javax.inject.Inject

class SavePouchItemsUseCase @Inject constructor(
    private val saveMapsUseCase: SaveMapsUseCase,
    private val saveBuildingsUseCase: SaveBuildingsUseCase,
    private val savePlantsUseCase: SavePlantsUseCase,
    private val saveGardenItemsUseCase: SaveGardenItemsUseCase,
    private val saveFurnitureUseCase: SaveFurnitureUseCase,
    private val saveRecipesUseCase: SaveRecipesUseCase,
    private val saveLocationsUseCase: SaveLocationsUseCase
) {
    suspend operator fun invoke(items: PouchItemsModel): PouchItemsModel =
        withContext(Dispatchers.IO) {
            val mapsWithLocalUrls =
                if (items.maps.isNotEmpty()) saveMapsUseCase(items.maps) else emptyList()

            val buildingsWithLocalUrls =
                if (items.buildings.isNotEmpty()) saveBuildingsUseCase(items.buildings) else emptyList()

            val plantsWithLocalUrls =
                if (items.plants.isNotEmpty()) savePlantsUseCase(items.plants) else emptyList()

            val gardenItemsWithLocalUrls =
                if (items.gardenItems.isNotEmpty()) saveGardenItemsUseCase(items.gardenItems) else emptyList()

            val furnitureWithLocalUrls =
                if (items.furniture.isNotEmpty()) saveFurnitureUseCase(items.furniture) else emptyList()

            val recipesWithLocalUrls =
                if (items.recipes.isNotEmpty()) saveRecipesUseCase(items.recipes) else emptyList()

            val locationsWithLocalUrls =
                if (items.locations.isNotEmpty()) saveLocationsUseCase(items.locations) else emptyList()

            items.copy(
                maps = mapsWithLocalUrls,
                buildings = buildingsWithLocalUrls,
                plants = plantsWithLocalUrls,
                gardenItems = gardenItemsWithLocalUrls,
                furniture = furnitureWithLocalUrls,
                recipes = recipesWithLocalUrls,
                locations = locationsWithLocalUrls
            )
        }
}