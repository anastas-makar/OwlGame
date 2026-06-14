package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.GardenType
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.FurnitureRepository
import pro.progr.owlgame.domain.repository.GardenItemsRepository
import pro.progr.owlgame.domain.repository.LocationsRepository
import pro.progr.owlgame.domain.repository.PlantsRepository
import pro.progr.owlgame.domain.repository.SuppliesRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import pro.progr.owlgame.presentation.mapper.toInventoryItemUi
import pro.progr.owlgame.presentation.ui.model.InventoryCategoryType
import pro.progr.owlgame.presentation.ui.model.InventoryCategoryUi
import pro.progr.owlgame.presentation.ui.model.InventoryUiState
import javax.inject.Inject

class InventoryViewModel @Inject constructor(
    buildingsRepository: BuildingsRepository,
    locationsRepository: LocationsRepository,
    furnitureRepository: FurnitureRepository,
    plantsRepository: PlantsRepository,
    gardenItemsRepository: GardenItemsRepository,
    supplyToRecipeRepository: SupplyToRecipeRepository,
    suppliesRepository: SuppliesRepository
) : ViewModel() {

    private val gardenItemsFlow = combine(
        gardenItemsRepository.getAvailableGardenItems(GardenType.KITCHEN_GARDEN),
        gardenItemsRepository.getAvailableGardenItems(GardenType.GARDEN),
        gardenItemsRepository.getAvailableGardenItems(GardenType.POOL)
    ) { kitchenGardenItems, gardenItems, poolItems ->
        kitchenGardenItems + gardenItems + poolItems
    }

    private val firstPartFlow = combine(
        buildingsRepository.getAvailableBuildings(),
        locationsRepository.getAvailableLocations(),
        furnitureRepository.getAvailableFurnitureItems()
    ) { buildings, locations, furniture ->
        FirstInventoryPart(
            buildings = buildings.map { it.toInventoryItemUi() },
            locations = locations.map { it.toInventoryItemUi() },
            furniture = furniture.map { it.toInventoryItemUi() }
        )
    }

    private val secondPartFlow = combine(
        plantsRepository.getAvailablePlants(),
        gardenItemsFlow,
        supplyToRecipeRepository.observeRecipes(),
        suppliesRepository.getAllAvailableSupplies()
    ) { plants, gardenItems, recipes, supplies ->
        SecondInventoryPart(
            plants = plants.map { it.toInventoryItemUi() },
            gardenItems = gardenItems.map { it.toInventoryItemUi() },
            recipes = recipes.map { it.toInventoryItemUi() },
            supplies = supplies
                .filter { it.amount > 0 }
                .map { it.toInventoryItemUi() }
        )
    }

    val uiState = combine(
        firstPartFlow,
        secondPartFlow
    ) { first, second ->
        InventoryUiState(
            categories = listOf(
                InventoryCategoryUi(
                    type = InventoryCategoryType.BUILDINGS,
                    titleRes = R.string.inventory_tab_buildings,
                    descriptionRes = R.string.inventory_description_buildings,
                    items = first.buildings
                ),
                InventoryCategoryUi(
                    type = InventoryCategoryType.LOCATIONS,
                    titleRes = R.string.inventory_tab_locations,
                    descriptionRes = R.string.inventory_description_locations,
                    items = first.locations
                ),
                InventoryCategoryUi(
                    type = InventoryCategoryType.FURNITURE,
                    titleRes = R.string.inventory_tab_furniture,
                    descriptionRes = R.string.inventory_description_furniture,
                    items = first.furniture
                ),
                InventoryCategoryUi(
                    type = InventoryCategoryType.PLANTS,
                    titleRes = R.string.inventory_tab_plants,
                    descriptionRes = R.string.inventory_description_plants,
                    items = second.plants
                ),
                InventoryCategoryUi(
                    type = InventoryCategoryType.GARDEN_ITEMS,
                    titleRes = R.string.inventory_tab_garden_items,
                    descriptionRes = R.string.inventory_description_garden_items,
                    items = second.gardenItems
                ),
                InventoryCategoryUi(
                    type = InventoryCategoryType.RECIPES,
                    titleRes = R.string.inventory_tab_recipes,
                    descriptionRes = R.string.inventory_description_recipes,
                    items = second.recipes
                ),
                InventoryCategoryUi(
                    type = InventoryCategoryType.SUPPLIES,
                    titleRes = R.string.inventory_tab_supplies,
                    descriptionRes = R.string.inventory_description_supplies,
                    items = second.supplies
                )
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = InventoryUiState()
    )

    private data class FirstInventoryPart(
        val buildings: List<pro.progr.owlgame.presentation.ui.model.InventoryItemUi>,
        val locations: List<pro.progr.owlgame.presentation.ui.model.InventoryItemUi>,
        val furniture: List<pro.progr.owlgame.presentation.ui.model.InventoryItemUi>
    )

    private data class SecondInventoryPart(
        val plants: List<pro.progr.owlgame.presentation.ui.model.InventoryItemUi>,
        val gardenItems: List<pro.progr.owlgame.presentation.ui.model.InventoryItemUi>,
        val recipes: List<pro.progr.owlgame.presentation.ui.model.InventoryItemUi>,
        val supplies: List<pro.progr.owlgame.presentation.ui.model.InventoryItemUi>
    )
}