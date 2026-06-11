package pro.progr.owlgame.presentation.mapper

import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.BuildingType
import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.domain.model.MapType
import pro.progr.owlgame.presentation.ui.model.LootHintType
import pro.progr.owlgame.presentation.ui.model.LootItemUi
import android.content.res.Resources

fun InPouchModel.toLootItems(resources: Resources): List<LootItemUi> {
    return buildList {
        diamonds?.let {
            add(
                LootItemUi(
                    id = "diamonds",
                    title = resources.getString(R.string.loot_diamonds_title),
                    description = resources.getString(R.string.loot_diamonds_description),
                    iconRes = R.drawable.ic_diamond_bright,
                    amount = it.amount,
                    hintType = LootHintType.DIAMONDS
                )
            )
        }

        buildings.forEach {
            val isHouse = it.type == BuildingType.HOUSE

            add(
                LootItemUi(
                    id = "building_${it.id}",
                    title = it.name,
                    description = resources.getString(
                        if (isHouse) R.string.loot_house_description
                        else R.string.loot_fortress_description
                    ),
                    imageUrl = it.imageUrl,
                    route = "building/${it.id}",
                    hintType = if (isHouse) LootHintType.HOUSE
                    else LootHintType.FORTRESS
                )
            )
        }

        maps.forEach {
            add(
                LootItemUi(
                    id = "map_${it.id}",
                    title = it.name,
                    description = resources.getString(
                        when (it.type) {
                            MapType.FREE -> R.string.loot_free_map_description
                            MapType.LOADING -> R.string.loot_loading_map_description
                            else -> R.string.loot_expedition_map_description
                        }
                    ),
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
                    id = "garden_item_${it.id}",
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
                    id = "plant_${it.id}",
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
                    id = "furniture_${it.id}",
                    title = it.name,
                    description = resources.getString(R.string.loot_furniture_description),
                    imageUrl = it.imageUrl,
                    hintType = LootHintType.FURNITURE
                )
            )
        }

        recipes.forEach {
            add(
                LootItemUi(
                    id = "recipe_${it.recipeId}",
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
                    id = "location_${it.id}",
                    title = it.name,
                    description = it.description.ifBlank {
                        resources.getString(R.string.loot_location_description)
                    },
                    imageUrl = it.imageUrl,
                    hintType = LootHintType.LOCATION
                )
            )
        }
    }
}