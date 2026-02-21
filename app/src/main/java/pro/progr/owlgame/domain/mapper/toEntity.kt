package pro.progr.owlgame.domain.mapper

import pro.progr.owlgame.data.db.Recipe
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.web.inpouch.RecipeInPouch
import pro.progr.owlgame.data.web.inpouch.SupplyInPouch

fun SupplyInPouch.toEntity(): Supply =
    Supply(
        id = id,
        imageUrl = imageUrl,
        name = name,
        description = description,
        amount = 0,              // ВАЖНО: с сервера amount игрока не приходит
        effectType = effectType,
        effectAmount = effectAmount
    )

fun RecipeInPouch.toEntity(): Recipe =
    Recipe(
        id = id,
        resSupplyId = resultSupply.id,
        description = description,
        effectType = effectType,
        effectAmount = effectAmount
    )

fun linkId(recipeId: String, supplyId: String): String =
    "${recipeId}__${supplyId}" // стабильный id для SupplyToRecipe
