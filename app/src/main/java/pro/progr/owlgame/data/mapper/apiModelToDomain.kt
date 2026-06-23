package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.web.AnimalApiModel
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus

fun AnimalApiModel.toDomain() =
    AnimalModel(
        id = id,
        kind = kind,
        name = null,
        initialDisplayName = initialDisplayName,
        imagePath = imagePath,
        status = AnimalStatus.SEARCHING,
        statusExpiresAt = null
    )