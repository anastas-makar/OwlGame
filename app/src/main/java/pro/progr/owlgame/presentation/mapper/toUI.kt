package pro.progr.owlgame.presentation.mapper

import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.GardenType
import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.domain.model.MapType
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
                    hint = "Бриллианты нужны, чтобы строить здания и устанавливать мебель. " +
                            "За бриллианты можно купить бомбочки и чешуйки, которые дают атаку и защиту в экспедиции. "
                )
            )
        }

        buildings.forEach {
            add(
                LootItemUi(
                    id = it.id,
                    title = it.name,
                    description = "Здание",
                    imageUrl = it.imageUrl,
                    route = "building/${it.id}",
                    hint =
                        "Домики и замки можно размещать в городах. Город можно основать на любой свободной карте. " +
                            "В домиках и замках могут селиться животные. Туда можно войти, посетить " +
                                "комнаты и огрод. В комнатах можно ставить мебель. " +
                                "Если в комнате есть холодильник, в ней можно готовить. " +
                                "В огороде можно выращивать растения. " +
                                "Рядом с замком есть не только огород, но и сад и пруд, " +
                                "в которых можно растить деревья, получать мёд, рыбу и многое другое."
                )
            )
        }

        maps.forEach {
            add(
                LootItemUi(
                    id = it.id,
                    title = it.name,
                    description = "Здесь можно основать город и строить здания",
                    imageUrl = it.imageUrl,
                    route = "map/${it.id}",
                    hint = when (it.type) {
                        MapType.FREE -> "Эта местность свободна. Вы можете основать здесь город. " +
                                "В городе можно строить дома, замки и создавать достопримечательности. " +
                                "В домах и замках могут селиться животные. " +
                                "В домах можно расставлять мебель. " +
                                "В садах и огродах рядом с домами и замками можно выращивать растения, разводить рыбу и многое другое. "
                        MapType.LOADING -> "Карта загружается."
                        else -> "Эта местность оккупирована монстрами. Вы не можете основать здесь город, пока не освободите её. " +
                                "Чтобы освободить карту, нужно отправить животное в экспедицию. Не забудьте снабдить животное " +
                                "припасами, бомбочками или чешуйками, которые дают очки защиты и атаки. " +
                                "Нужно много очков защиты и атаки, чтобы победить в бою!"
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