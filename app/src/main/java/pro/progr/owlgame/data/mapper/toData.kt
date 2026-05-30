package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.db.entity.Animal
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.entity.ExpeditionMedal
import pro.progr.owlgame.data.db.entity.Furniture
import pro.progr.owlgame.data.db.entity.Garden
import pro.progr.owlgame.data.db.entity.GardenItem
import pro.progr.owlgame.data.db.entity.Location
import pro.progr.owlgame.data.db.entity.LocationScene
import pro.progr.owlgame.data.db.entity.Plant
import pro.progr.owlgame.data.db.entity.RoomEntity
import pro.progr.owlgame.data.db.entity.Supply
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.ExpeditionMedalModel
import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.model.GardenItemModel
import pro.progr.owlgame.domain.model.GardenModel
import pro.progr.owlgame.domain.model.LocationSceneModel
import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.domain.model.PlantModel
import pro.progr.owlgame.domain.model.RoomModel
import pro.progr.owlgame.domain.model.SupplyModel
import pro.progr.owlgame.data.db.model.AnimalStatus as DbAnimalStatus
import pro.progr.owlgame.domain.model.AnimalStatus as DomainAnimalStatus
import pro.progr.owlgame.data.db.model.BuildingType as DbBuildingType
import pro.progr.owlgame.domain.model.BuildingType as DomainBuildingType
import pro.progr.owlgame.data.db.model.GardenType as DbGardenType
import pro.progr.owlgame.domain.model.GardenType as DomainGardenType
import pro.progr.owlgame.data.db.model.EffectType as DbEffectType
import pro.progr.owlgame.domain.model.EffectType as DomainEffectType
import pro.progr.owlgame.data.db.model.FurnitureType as DbFurnitureType
import pro.progr.owlgame.domain.model.FurnitureType as DomainFurnitureType
import pro.progr.owlgame.data.db.model.ItemType as DbItemType
import pro.progr.owlgame.domain.model.ItemType as DomainItemType
import pro.progr.owlgame.data.db.model.MapType as DbMapType
import pro.progr.owlgame.domain.model.MapType as DomainMapType
import pro.progr.owlgame.data.db.model.StreetDirection as DbStreetDirection
import pro.progr.owlgame.domain.model.StreetDirection as DomainStreetDirection
import pro.progr.owlgame.data.model.LocationType as DbLocationType
import pro.progr.owlgame.domain.model.LocationType as DomainLocationType


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

fun DomainEffectType.toData(): DbEffectType =
    when (this) {
        DomainEffectType.NO_EFFECT -> DbEffectType.NO_EFFECT
        DomainEffectType.DAMAGE -> DbEffectType.DAMAGE
        DomainEffectType.HEAL -> DbEffectType.HEAL
    }

fun DomainFurnitureType.toData() : DbFurnitureType  =
    when (this) {
        DomainFurnitureType.REFRIGERATOR -> DbFurnitureType.REFRIGERATOR
        DomainFurnitureType.OTHER -> DbFurnitureType.OTHER
    }

fun DomainItemType.toData(): DbItemType =
    when (this) {
        DomainItemType.TREE -> DbItemType.TREE
        DomainItemType.FISH -> DbItemType.FISH
        DomainItemType.WATER_PLANT -> DbItemType.WATER_PLANT
        DomainItemType.HIVE -> DbItemType.HIVE
        DomainItemType.ANIMAL_HOUSE -> DbItemType.ANIMAL_HOUSE
        DomainItemType.NEST -> DbItemType.NEST
        DomainItemType.MUSHROOM -> DbItemType.MUSHROOM
    }

fun DomainMapType.toData(): DbMapType =
    when (this) {
        DomainMapType.FREE -> DbMapType.FREE
        DomainMapType.OCCUPIED -> DbMapType.OCCUPIED
        DomainMapType.EXPEDITION -> DbMapType.EXPEDITION
        DomainMapType.TOWN -> DbMapType.TOWN
        DomainMapType.LOADING -> error("Attempt to process unloaded map")
    }

fun DomainLocationType.toData(): DbLocationType =
    when (this) {
        DomainLocationType.WATER_ANOMALY -> DbLocationType.WATER_ANOMALY
        DomainLocationType.FOUNTAIN -> DbLocationType.FOUNTAIN
        DomainLocationType.WATERFALL -> DbLocationType.WATERFALL
        DomainLocationType.LANDMARK -> DbLocationType.LANDMARK
        DomainLocationType.MONUMENT -> DbLocationType.MONUMENT
        DomainLocationType.CAVE -> DbLocationType.CAVE
        DomainLocationType.PARK -> DbLocationType.PARK
        DomainLocationType.PAVILION -> DbLocationType.PAVILION
        DomainLocationType.RESORT -> DbLocationType.RESORT
        DomainLocationType.RUINS -> DbLocationType.RUINS
    }

fun DomainStreetDirection.toData() : DbStreetDirection =
    when (this) {
        DomainStreetDirection.WEST_TO_EAST -> DbStreetDirection.WEST_TO_EAST
        DomainStreetDirection.NORTH_TO_SOUTH -> DbStreetDirection.NORTH_TO_SOUTH
    }



fun AnimalModel.toData(): Animal =
    Animal(
        id = id,
        kind = kind,
        name = name,
        imagePath = imagePath,
        status = status.toData()
    )

fun GardenModel.toData(buildingId : String) : Garden =
    Garden(
        id = id,
        name = name,
        imageUrl = imageUrl,
        buildingId = buildingId,
        gardenNumber = gardenNumber,
        gardenType = gardenType.toData()
    )

fun RoomModel.toData(buildingId : String) : RoomEntity =
    RoomEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        buildingId = buildingId,
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

fun SupplyModel.toData(): Supply =
    Supply(
        id = id,
        imageUrl = imageUrl,
        name = name,
        description = description,
        amount = amount,
        effectType = effectType.toData(),
        effectAmount = effectAmount
    )

fun FurnitureModel.toData(): Furniture =
    Furniture(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        roomId = roomId,
        x = x,
        y = y,
        height = height,
        width = width,
        type = type.toData())


fun GardenItemModel.toData(): GardenItem =
    GardenItem(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        gardenId = gardenId,
        x = x,
        y = y,
        supplyId = supplyId,
        supplyAmount = supplyAmount,
        itemType = itemType.toData(),
        gardenType = gardenType.toData(),
        readiness = readiness,
        deleted = deleted
    )
fun PlantModel.toData(): Plant =
    Plant(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        gardenId = gardenId,
        x = x,
        y = y,
        supplyId = supplyId,
        supplyAmount = supplyAmount,
        seedAmount = seedAmount,
        readiness = readiness,
        deleted = deleted
    )

fun ExpeditionMedalModel.toData() =
    ExpeditionMedal(
        id = id,
        animalId = animalId,
        expeditionId = expeditionId,
        mapId = mapId,
        title = title,
        description = description,
        imageUrl = imageUrl
    )

fun LocationWithScenesModel.toEntity(mapId : String?) =
    Location(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        mapId = mapId,
        price = price,
        x = x,
        y = y,
        type = type.toData()
    )

fun LocationSceneModel.toData(locationId : String) =
    LocationScene(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        locationId = locationId,
        sceneNumber = sceneNumber
    )