package pro.progr.owlgame.presentation.mapper

import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.presentation.ui.model.LootItemUi

fun InPouchModel.toLootItems(): List<LootItemUi> {
    return buildList {
        diamonds?.let {
            add(
                LootItemUi(
                    id = "diamonds",
                    title = "Бриллианты",
                    description = "Они нужны, чтобы покупать вещи и строить здания",
                    iconRes = R.drawable.ic_diamond_bright,
                    amount = it.amount
                )
            )
        }

        buildings.forEach {
            add(
                LootItemUi(
                    id = "building_${it.id}",
                    title = it.name,
                    description = "Здание",
                    imageUrl = it.imageUrl,
                    route = "building/${it.id}"
                )
            )
        }

        maps.forEach {
            add(
                LootItemUi(
                    id = "map_${it.id}",
                    title = it.name,
                    description = "Здесь можно основать город и строить здания",
                    imageUrl = it.imageUrl,
                    route = "map/${it.id}"
                )
            )
        }

        gardenItems.forEach {
            add(
                LootItemUi(
                    id = "garden_item_${it.id}",
                    title = it.name,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    amount = it.supplyAmount
                )
            )
        }

        plants.forEach {
            add(
                LootItemUi(
                    id = "plant_${it.id}",
                    title = it.name,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    amount = it.seedAmount
                )
            )
        }

        furniture.forEach {
            add(
                LootItemUi(
                    id = "furniture_${it.id}",
                    title = it.name,
                    description = "Можно поставить в комнате",
                    imageUrl = it.imageUrl
                )
            )
        }

        recipes.forEach {
            add(
                LootItemUi(
                    id = "recipe_${it.recipeId}",
                    title = it.resultName,
                    description = it.description,
                    imageUrl = it.resultImageUrl
                )
            )
        }

        locations.forEach {
            add(
                LootItemUi(
                    id = "location_${it.id}",
                    title = it.name,
                    description = it.description.ifBlank { "Достопримечательность" },
                    imageUrl = it.imageUrl
                )
            )
        }
    }
}