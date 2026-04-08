package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.db.embedded.MapWithData
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.entity.MapEntity
import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapWithBuildingsModel
import pro.progr.owlgame.data.db.model.MapType as DbMapType
import pro.progr.owlgame.domain.model.MapType as DomainMapType
import pro.progr.owlgame.data.db.model.BuildingType as DbBuildingType
import pro.progr.owlgame.domain.model.BuildingType as DomainBuildingType

fun DbMapType.toDomain(): DomainMapType =
    when (this) {
        DbMapType.FREE -> DomainMapType.FREE
        DbMapType.OCCUPIED -> DomainMapType.OCCUPIED
        DbMapType.EXPEDITION -> DomainMapType.EXPEDITION
        DbMapType.TOWN -> DomainMapType.TOWN
    }

fun DbBuildingType.toDomain(): DomainBuildingType =
    when (this) {
        DbBuildingType.FORTRESS -> DomainBuildingType.FORTRESS
        DbBuildingType.HOUSE -> DomainBuildingType.HOUSE
    }

fun MapEntity.toDomain(): MapModel =
    MapModel(
        id = id,
        name = name,
        imageUrl = imagePath,
        type = type.toDomain()
    )

fun MapWithData.toDomain(): MapWithBuildingsModel =
    MapWithBuildingsModel(
        id = mapEntity.id,
        name = mapEntity.name,
        imageUrl = mapEntity.imagePath,
        type = mapEntity.type.toDomain(),
        buildings = buildings.map {
            it.toDomain()
        }
    )

fun Building.toDomain(): BuildingModel =
    BuildingModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        price = price,
        animalId = animalId,
        x = x,
        y = y,
        type = type.toDomain()
    )