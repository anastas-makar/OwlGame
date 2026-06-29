package pro.progr.owlgame.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.model.PouchItemsModel
import javax.inject.Inject

class SavePouchUseCase @Inject constructor(
    private val saveMapsUseCase: SaveMapsUseCase,
    private val saveBuildingsUseCase: SaveBuildingsUseCase,
    private val savePlantsUseCase: SavePlantsUseCase,
    private val saveGardenItemsUseCase: SaveGardenItemsUseCase,
    private val saveFurnitureUseCase: SaveFurnitureUseCase,
    private val saveRecipesUseCase: SaveRecipesUseCase,
    private val saveLocationsUseCase: SaveLocationsUseCase) {

    suspend operator fun invoke(webPouch: PouchItemsModel,
                                diamondDao: PurchaseInterface) : PouchItemsModel {
        val mapsWithLocalUrls = withContext(Dispatchers.IO) {
            if (webPouch.maps.isNotEmpty()) {
                saveMapsUseCase(webPouch.maps)
            } else emptyList()
        }

        val buildingsWithLocalUrls = withContext(Dispatchers.IO) {
            if (webPouch.buildings.isNotEmpty()) {
                saveBuildingsUseCase(webPouch.buildings)
            } else emptyList()
        }

        val plantsWithLocalUrls = withContext(Dispatchers.IO) {
            if (webPouch.plants.isNotEmpty()) {
                savePlantsUseCase(webPouch.plants)
            } else emptyList()
        }

        if (webPouch.diamonds != null) {
            withContext(Dispatchers.IO) {
                diamondDao.spendDiamonds(-webPouch.diamonds.amount)
            }
        }

        val recipesWithLocalUrls = if (webPouch.recipes.isNotEmpty()) {
            withContext(Dispatchers.IO) {
                saveRecipesUseCase(webPouch.recipes)
            }
        } else emptyList()

        val gardenItemsWithLocalUrls = withContext(Dispatchers.IO) {
            if (webPouch.gardenItems.isNotEmpty()) {
                saveGardenItemsUseCase(webPouch.gardenItems)
            } else emptyList()
        }

        val furnitureWithLocalUrls = withContext(Dispatchers.IO) {
            if (webPouch.furniture.isNotEmpty()) {
                saveFurnitureUseCase(webPouch.furniture)
            } else emptyList()
        }

        val locationsWithLocalUrls = withContext(Dispatchers.IO) {
            if (webPouch.locations.isNotEmpty()) {
                saveLocationsUseCase(webPouch.locations)
            } else emptyList()
        }

        return PouchItemsModel(
            buildings = buildingsWithLocalUrls,
            maps = mapsWithLocalUrls,
            plants = plantsWithLocalUrls,
            gardenItems = gardenItemsWithLocalUrls,
            diamonds = webPouch.diamonds,
            furniture = furnitureWithLocalUrls,
            recipes = recipesWithLocalUrls,
            locations = locationsWithLocalUrls
        )
    }
}