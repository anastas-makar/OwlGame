package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.db.entity.Animal
import pro.progr.owlgame.data.db.entity.Recipe
import pro.progr.owlgame.data.db.entity.Supply
import pro.progr.owlgame.data.web.inpouch.RecipeInPouch
import pro.progr.owlgame.data.web.inpouch.SupplyInPouch
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.data.db.model.AnimalStatus as DbAnimalStatus
import pro.progr.owlgame.domain.model.AnimalStatus as DomainAnimalStatus

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
        description = description
    )

fun linkId(recipeId: String, supplyId: String): String =
    "${recipeId}__${supplyId}" // стабильный id для SupplyToRecipe

fun DomainAnimalStatus.toData(): DbAnimalStatus =
    when (this) {
        DomainAnimalStatus.EXPEDITION -> DbAnimalStatus.EXPEDITION
        DomainAnimalStatus.FUGITIVE -> DbAnimalStatus.FUGITIVE
        DomainAnimalStatus.PET -> DbAnimalStatus.PET
        DomainAnimalStatus.SEARCHING -> DbAnimalStatus.SEARCHING
        DomainAnimalStatus.GONE -> DbAnimalStatus.GONE
    }

fun AnimalModel.toData(): Animal =
    Animal(
        id = id,
        kind = kind,
        name = name,
        imagePath = imagePath,
        status = status.toData()
    )
