package pro.progr.owlgame.presentation.mapper

import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.BuildingType
import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.domain.model.MapType
import pro.progr.owlgame.presentation.ui.model.LootHintType
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
                    amount = it.amount,
                    hintType = LootHintType.DIAMONDS
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
                    route = "building/${it.id}",
                    hintType = if (it.type == BuildingType.HOUSE) LootHintType.HOUSE
                        else LootHintType.FORTRESS
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
                    route = "map/${it.id}",
                    hintType = when (it.type) {
                        MapType.FREE -> LootHintType.FREE_MAP
                        MapType.LOADING -> null
                        else -> LootHintType.EXPEDITION_MAP
                    }
                )
            )
        }

        gardenItems.forEach {
            add(
                LootItemUi(
                    id = it.id,
                    title = it.name,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    hintType = LootHintType.GARDEN_ITEM
                )
            )
        }

        plants.forEach {
            add(
                LootItemUi(
                    id = it.id,
                    title = it.name,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    hintType = LootHintType.PLANT
                )
            )
        }

        furniture.forEach {
            add(
                LootItemUi(
                    id = it.id,
                    title = it.name,
                    description = "Можно поставить в комнате",
                    imageUrl = it.imageUrl,
                    hintType = LootHintType.FURNITURE
                )
            )
        }

        recipes.forEach {
            add(
                LootItemUi(
                    id = it.recipeId,
                    title = it.resultName,
                    description = it.description,
                    imageUrl = it.resultImageUrl,
                    hintType = LootHintType.RECIPE
                )
            )
        }

        locations.forEach {
            add(
                LootItemUi(
                    id = it.id,
                    title = it.name,
                    description = it.description.ifBlank { "Достопримечательность" },
                    imageUrl = it.imageUrl,
                    hintType = LootHintType.LOCATION
                )
            )
        }
    }
}