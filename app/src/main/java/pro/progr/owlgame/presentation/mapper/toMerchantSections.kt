package pro.progr.owlgame.presentation.mapper

import pro.progr.owlgame.domain.model.PouchItemsModel
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantItemExtraInfo
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantItemKey
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopItemUi
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopSectionType
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopSectionUi

fun PouchItemsModel.toMerchantSections(
    knownRecipeIds: Set<String> = emptySet()
): List<MerchantShopSectionUi> {
    return buildList {
        if (buildings.isNotEmpty()) {
            add(
                MerchantShopSectionUi(
                    type = MerchantShopSectionType.BUILDINGS,
                    items = buildings.map { building ->
                        MerchantShopItemUi(
                            key = MerchantItemKey.Building(building.id),
                            title = building.name,
                            description = null,
                            imageUrl = building.imageUrl,
                            extraInfo = MerchantItemExtraInfo.BuildingCost(building.price)
                        )
                    }
                )
            )
        }

        if (maps.isNotEmpty()) {
            add(
                MerchantShopSectionUi(
                    type = MerchantShopSectionType.MAPS,
                    items = maps.map { map ->
                        MerchantShopItemUi(
                            key = MerchantItemKey.Map(map.id),
                            title = map.name,
                            description = null,
                            imageUrl = map.imageUrl
                        )
                    }
                )
            )
        }

        if (locations.isNotEmpty()) {
            add(
                MerchantShopSectionUi(
                    type = MerchantShopSectionType.LOCATIONS,
                    items = locations.map { location ->
                        MerchantShopItemUi(
                            key = MerchantItemKey.Location(location.id),
                            title = location.name,
                            description = location.description,
                            imageUrl = location.imageUrl
                        )
                    }
                )
            )
        }

        if (furniture.isNotEmpty()) {
            add(
                MerchantShopSectionUi(
                    type = MerchantShopSectionType.FURNITURE,
                    items = furniture.map { furnitureItem ->
                        MerchantShopItemUi(
                            key = MerchantItemKey.Furniture(furnitureItem.id),
                            title = furnitureItem.name,
                            description = null,
                            imageUrl = furnitureItem.imageUrl,
                            extraInfo = MerchantItemExtraInfo.FurnitureInstallCost(
                                furnitureItem.price
                            )
                        )
                    }
                )
            )
        }

        if (plants.isNotEmpty()) {
            add(
                MerchantShopSectionUi(
                    type = MerchantShopSectionType.PLANTS,
                    items = plants.map { plant ->
                        MerchantShopItemUi(
                            key = MerchantItemKey.Plant(plant.id),
                            title = plant.name,
                            description = plant.description,
                            imageUrl = plant.imageUrl,
                            extraInfo = MerchantItemExtraInfo.SeedAmount(plant.seedAmount)
                        )
                    }
                )
            )
        }

        if (gardenItems.isNotEmpty()) {
            add(
                MerchantShopSectionUi(
                    type = MerchantShopSectionType.GARDEN_ITEMS,
                    items = gardenItems.map { gardenItem ->
                        MerchantShopItemUi(
                            key = MerchantItemKey.GardenItem(gardenItem.id),
                            title = gardenItem.name,
                            description = gardenItem.description,
                            imageUrl = gardenItem.imageUrl
                        )
                    }
                )
            )
        }

        val recipesToShow = recipes.filterNot { it.recipeId in knownRecipeIds }

        if (recipesToShow.isNotEmpty()) {
            add(
                MerchantShopSectionUi(
                    type = MerchantShopSectionType.RECIPES,
                    items = recipesToShow.map { recipe ->
                        MerchantShopItemUi(
                            key = MerchantItemKey.Recipe(recipe.recipeId),
                            title = recipe.resultSupply.name,
                            description = recipe.description,
                            imageUrl = recipe.resultSupply.imageUrl,
                            extraInfo = MerchantItemExtraInfo.RecipeUnlock
                        )
                    }
                )
            )
        }
    }
}