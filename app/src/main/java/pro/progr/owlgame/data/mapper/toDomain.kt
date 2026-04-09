package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.db.embedded.BuildingWithAnimal
import pro.progr.owlgame.data.db.embedded.BuildingWithData
import pro.progr.owlgame.data.db.embedded.MapWithData
import pro.progr.owlgame.data.db.entity.Animal
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.entity.Garden
import pro.progr.owlgame.data.db.entity.MapEntity
import pro.progr.owlgame.data.db.entity.RoomEntity
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.BuildingWithDataModel
import pro.progr.owlgame.domain.model.GardenModel
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapWithBuildingsModel
import pro.progr.owlgame.domain.model.RoomModel
import pro.progr.owlgame.data.db.model.MapType as DbMapType
import pro.progr.owlgame.domain.model.MapType as DomainMapType
import pro.progr.owlgame.data.db.model.BuildingType as DbBuildingType
import pro.progr.owlgame.domain.model.BuildingType as DomainBuildingType
import pro.progr.owlgame.data.db.model.AnimalStatus as DbAnimalStatus
import pro.progr.owlgame.domain.model.AnimalStatus as DomainAnimalStatus
import pro.progr.owlgame.data.db.model.GardenType as DbGardenType
import pro.progr.owlgame.domain.model.GardenType as DomainGardenType

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

fun DbAnimalStatus.toDomain(): DomainAnimalStatus =
    when (this) {
        DbAnimalStatus.EXPEDITION -> DomainAnimalStatus.EXPEDITION
        DbAnimalStatus.FUGITIVE -> DomainAnimalStatus.FUGITIVE
        DbAnimalStatus.PET -> DomainAnimalStatus.PET
        DbAnimalStatus.SEARCHING -> DomainAnimalStatus.SEARCHING
        DbAnimalStatus.GONE -> DomainAnimalStatus.GONE
    }

fun DbGardenType.toDomain(): DomainGardenType =
    when (this) {
        DbGardenType.KITCHEN_GARDEN -> DomainGardenType.KITCHEN_GARDEN
        DbGardenType.GARDEN -> DomainGardenType.GARDEN
        DbGardenType.POOL -> DomainGardenType.POOL
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
        mapId = mapId,
        price = price,
        animalId = animalId,
        x = x,
        y = y,
        type = type.toDomain()
    )

fun Animal.toDomain(): AnimalModel =
    AnimalModel(
        id = id,
        kind = kind,
        name = name,
        imagePath = imagePath,
        status = status.toDomain()
    )

fun RoomEntity.toDomain(): RoomModel =
    RoomModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        buildingId = buildingId,
        roomNumber = roomNumber
    )

fun Garden.toDomain(): GardenModel =
    GardenModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        buildingId = buildingId,
        gardenNumber = gardenNumber,
        gardenType = gardenType.toDomain()
    )

fun BuildingWithAnimal.toDomain(): BuildingWithAnimalModel =
    BuildingWithAnimalModel(
        id = building.id,
        name = building.name,
        imageUrl = building.imageUrl,
        mapId = building.mapId,
        price = building.price,
        animal = animal?.toDomain(),
        x = building.x,
        y = building.y,
        type = building.type.toDomain()
    )

fun BuildingWithData.toDomain(): BuildingWithDataModel =
    BuildingWithDataModel(
        id = building.id,
        name = building.name,
        imageUrl = building.imageUrl,
        mapId = building.mapId,
        price = building.price,
        animal = animal?.toDomain(),
        x = building.x,
        y = building.y,
        type = building.type.toDomain(),
        rooms = rooms.map { it.toDomain() },
        gardens = gardens.map { it.toDomain() })