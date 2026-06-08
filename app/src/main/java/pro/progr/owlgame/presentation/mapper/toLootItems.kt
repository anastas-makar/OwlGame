package pro.progr.owlgame.presentation.mapper

import pro.progr.owlgame.R
3import pro.progr.owlgame.domain.model.BuildingType
import pro.progr.owlgame.domain.model.GardenType
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
                    hint = "Возле каждого замка есть огород, сад и пруд. " +
                            "${it.name.replaceFirstChar { ch -> ch.uppercase() }} можно расместить в " +
                            when (it.gardenType) {
                                GardenType.GARDEN -> "саду"
                                GardenType.POOL -> "пруду"
                                GardenType.KITCHEN_GARDEN -> "огороде"
                                } +
                                ". Вы сможете заходить туда и собирать припас: ${it.supply.name}. " +
                                "${it.name.replaceFirstChar { ch -> ch.uppercase() }} " +
                                "не пропадёт после сбора приппасов."
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
                    hint = "Это растение можно посадить. Около каждого домика или замка есть огород. " +
                            "В огороде можно сажать растения. Когда растение вырастет, можно собрать его семена или заготовить припасы. " +
                            "Припасы можно использовать в экспедиции: они дают атаку или защиту."
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
                    hint = "Мебель можно размещать в комнатах в домиках или замках. Важный предмет мебели — холодильник. " +
                            "Если в домике живёт животное, а в комнате стоит холодильник, то в этой комнате можно готовить."
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
                    hint = "Из простых припасов можно готовить новые сложные припасы по рецептам. Но готовить можно не везде. " +
                            "Нужна комната с холодильником, чтобы готовить. " +
                            "Поселите животное в домике и поставьте в этом домике холодильник, " +
                            "и тогда животное сможет готовить."
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
                    hint = "Достопримечательности можно размещать в городе. Можно заходить внутрь и смотреть, что там происходит. " +
                            "Внутри достопримечательностей нельзя селить животных: они селятся только в домиках или замках. "
                )
            )
        }
    }
}