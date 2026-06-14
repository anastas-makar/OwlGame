package pro.progr.owlgame.presentation.mapper

import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.model.GardenItemModel
import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.domain.model.PlantModel
import pro.progr.owlgame.domain.model.RecipeModel
import pro.progr.owlgame.domain.model.SupplyModel
import pro.progr.owlgame.presentation.ui.model.InventoryItemUi

fun BuildingModel.toInventoryItemUi(): InventoryItemUi =
    InventoryItemUi(
        id = "building_$id",
        title = name,
        imageUrl = imageUrl
    )

fun LocationWithScenesModel.toInventoryItemUi(): InventoryItemUi =
    InventoryItemUi(
        id = "location_$id",
        title = name,
        imageUrl = imageUrl
    )

fun FurnitureModel.toInventoryItemUi(): InventoryItemUi =
    InventoryItemUi(
        id = "furniture_$id",
        title = name,
        imageUrl = imageUrl
    )

fun PlantModel.toInventoryItemUi(): InventoryItemUi =
    InventoryItemUi(
        id = "plant_$id",
        title = name,
        imageUrl = imageUrl
    )

fun GardenItemModel.toInventoryItemUi(): InventoryItemUi =
    InventoryItemUi(
        id = "garden_item_$id",
        title = name,
        imageUrl = imageUrl
    )

fun RecipeModel.toInventoryItemUi(): InventoryItemUi =
    InventoryItemUi(
        id = "recipe_$recipeId",
        title = resultName,
        imageUrl = resultImageUrl
    )

fun SupplyModel.toInventoryItemUi(): InventoryItemUi =
    InventoryItemUi(
        id = "supply_$id",
        title = name,
        imageUrl = imageUrl,
        amount = amount
    )