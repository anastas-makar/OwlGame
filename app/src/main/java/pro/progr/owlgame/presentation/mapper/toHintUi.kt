package pro.progr.owlgame.presentation.mapper

import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.LootHintType
import pro.progr.owlgame.presentation.ui.model.LootHintUi
import pro.progr.owlgame.presentation.ui.model.LootItemUi

fun LootHintType.toHintUi(): LootHintUi =
    when (this) {
        LootHintType.DIAMONDS -> LootHintUi(
            id = "hint_diamonds",
            titleRes = R.string.hint_diamonds_title,
            paragraphsRes = R.array.hint_diamonds_paragraphs
        )

        LootHintType.HOUSE -> LootHintUi(
            id = "hint_house",
            titleRes = R.string.hint_house_title,
            paragraphsRes = R.array.hint_house_paragraphs
        )

        LootHintType.FORTRESS -> LootHintUi(
            id = "hint_castle",
            titleRes = R.string.hint_fortress_title,
            paragraphsRes = R.array.hint_fortress_paragraphs
        )

        LootHintType.FREE_MAP -> LootHintUi(
            id = "hint_free_map",
            titleRes = R.string.hint_free_map_title,
            paragraphsRes = R.array.hint_free_map_paragraphs
        )

        LootHintType.EXPEDITION_MAP -> LootHintUi(
            id = "hint_expedition_map",
            titleRes = R.string.hint_expedition_map_title,
            paragraphsRes = R.array.hint_expedition_map_paragraphs
        )

        LootHintType.GARDEN_ITEM -> LootHintUi(
            id = "hint_garden_item",
            titleRes = R.string.hint_garden_item_title,
            paragraphsRes = R.array.hint_garden_item_paragraphs
        )

        LootHintType.PLANT -> LootHintUi(
            id = "hint_plant",
            titleRes = R.string.hint_plant_title,
            paragraphsRes = R.array.hint_plant_paragraphs
        )

        LootHintType.FURNITURE -> LootHintUi(
            id = "hint_furniture",
            titleRes = R.string.hint_furniture_title,
            paragraphsRes = R.array.hint_furniture_paragraphs
        )

        LootHintType.RECIPE -> LootHintUi(
            id = "hint_recipe",
            titleRes = R.string.hint_recipe_title,
            paragraphsRes = R.array.hint_recipe_paragraphs
        )

        LootHintType.LOCATION -> LootHintUi(
            id = "hint_location",
            titleRes = R.string.hint_location_title,
            paragraphsRes = R.array.hint_location_paragraphs
        )
    }

fun List<LootItemUi>.toLootHints(): List<LootHintUi> =
    mapNotNull { it.hintType }
        .distinct()
        .map { it.toHintUi() }