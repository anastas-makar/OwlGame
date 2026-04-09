package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.db.entity.Animal
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.entity.Garden
import pro.progr.owlgame.data.db.entity.RoomEntity
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.GardenModel
import pro.progr.owlgame.domain.model.RoomModel
import pro.progr.owlgame.data.db.model.AnimalStatus as DbAnimalStatus
import pro.progr.owlgame.domain.model.AnimalStatus as DomainAnimalStatus
import pro.progr.owlgame.data.db.model.BuildingType as DbBuildingType
import pro.progr.owlgame.domain.model.BuildingType as DomainBuildingType
import pro.progr.owlgame.data.db.model.GardenType as DbGardenType
import pro.progr.owlgame.domain.model.GardenType as DomainGardenType


fun DomainAnimalStatus.toData(): DbAnimalStatus =
    when (this) {
        DomainAnimalStatus.EXPEDITION -> DbAnimalStatus.EXPEDITION
        DomainAnimalStatus.FUGITIVE -> DbAnimalStatus.FUGITIVE
        DomainAnimalStatus.PET -> DbAnimalStatus.PET
        DomainAnimalStatus.SEARCHING -> DbAnimalStatus.SEARCHING
        DomainAnimalStatus.GONE -> DbAnimalStatus.GONE
    }

fun DomainBuildingType.toData(): DbBuildingType =
    when (this) {
        DomainBuildingType.HOUSE -> DbBuildingType.HOUSE
        DomainBuildingType.FORTRESS -> DbBuildingType.FORTRESS
    }

fun DomainGardenType.toData(): DbGardenType =
    when (this) {
        DomainGardenType.KITCHEN_GARDEN -> DbGardenType.KITCHEN_GARDEN
        DomainGardenType.GARDEN -> DbGardenType.GARDEN
        DomainGardenType.POOL -> DbGardenType.POOL
    }

fun AnimalModel.toData(): Animal =
    Animal(
        id = id,
        kind = kind,
        name = name,
        imagePath = imagePath,
        status = status.toData()
    )

fun GardenModel.toData() : Garden =
    Garden(
        id = id,
        name = name,
        imageUrl = imageUrl,
        buildingId = id,
        gardenNumber = gardenNumber,
        gardenType = gardenType.toData()
    )

fun RoomModel.toData() : RoomEntity =
    RoomEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        buildingId = id,
        roomNumber = roomNumber
    )

fun BuildingModel.toData(): Building =
    Building(
        id = id,
        name = name,
        imageUrl = imageUrl,
        mapId = mapId,
        price = price,
        animalId = animalId,
        x = x,
        y = y,
        type = type.toData()
    )
