package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.db.embedded.BuildingWithAnimal
import pro.progr.owlgame.data.db.embedded.BuildingWithData
import pro.progr.owlgame.data.db.embedded.ExpeditionWithData
import pro.progr.owlgame.data.db.embedded.MapWithData
import pro.progr.owlgame.data.db.entity.Animal
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.entity.Enemy
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.db.entity.Furniture
import pro.progr.owlgame.data.db.entity.Garden
import pro.progr.owlgame.data.db.entity.GardenItem
import pro.progr.owlgame.data.db.entity.MapEntity
import pro.progr.owlgame.data.db.entity.Plant
import pro.progr.owlgame.data.db.entity.RoomEntity
import pro.progr.owlgame.data.db.entity.Supply
import pro.progr.owlgame.data.web.Pouch
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.BuildingModel
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.BuildingWithDataModel
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.domain.model.ExpeditionModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.model.GardenItemModel
import pro.progr.owlgame.domain.model.GardenModel
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapWithBuildingsModel
import pro.progr.owlgame.domain.model.PlantModel
import pro.progr.owlgame.domain.model.PouchModel
import pro.progr.owlgame.domain.model.RoomModel
import pro.progr.owlgame.domain.model.SupplyModel
import pro.progr.owlgame.data.db.model.MapType as DbMapType
import pro.progr.owlgame.domain.model.MapType as DomainMapType
import pro.progr.owlgame.data.db.model.BuildingType as DbBuildingType
import pro.progr.owlgame.domain.model.BuildingType as DomainBuildingType
import pro.progr.owlgame.data.db.model.AnimalStatus as DbAnimalStatus
import pro.progr.owlgame.domain.model.AnimalStatus as DomainAnimalStatus
import pro.progr.owlgame.data.db.model.GardenType as DbGardenType
import pro.progr.owlgame.domain.model.GardenType as DomainGardenType
import pro.progr.owlgame.data.db.model.EffectType as DbEffectType
import pro.progr.owlgame.domain.model.EffectType as DomainEffectType
import pro.progr.owlgame.data.model.ExpeditionStatus as DbExpeditionStatus
import pro.progr.owlgame.domain.model.ExpeditionStatus as DomainExpeditionStatus
import pro.progr.owlgame.data.db.model.FurnitureType as DbFurnitureType
import pro.progr.owlgame.domain.model.FurnitureType as DomainFurnitureType
import pro.progr.owlgame.data.db.model.ItemType as DbItemType
import pro.progr.owlgame.domain.model.ItemType as DomainItemType

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

fun DbEffectType.toDomain() : DomainEffectType =
    when (this) {
        DbEffectType.NO_EFFECT -> DomainEffectType.NO_EFFECT
        DbEffectType.HEAL -> DomainEffectType.HEAL
        DbEffectType.DAMAGE -> DomainEffectType.DAMAGE
    }

fun DbExpeditionStatus.toDomain() : DomainExpeditionStatus =
    when (this) {
        DbExpeditionStatus.ACTIVE -> DomainExpeditionStatus.ACTIVE
        DbExpeditionStatus.WON -> DomainExpeditionStatus.WON
        DbExpeditionStatus.LOST -> DomainExpeditionStatus.LOST
    }

fun DbFurnitureType.toDomain(): DomainFurnitureType =
    when (this) {
        DbFurnitureType.REFRIGERATOR -> DomainFurnitureType.REFRIGERATOR
        DbFurnitureType.OTHER -> DomainFurnitureType.OTHER
    }

fun DbItemType.toDomain(): DomainItemType =
    when (this) {
        DbItemType.TREE -> DomainItemType.TREE
        DbItemType.FISH -> DomainItemType.FISH
        DbItemType.WATER_PLANT -> DomainItemType.WATER_PLANT
        DbItemType.HIVE -> DomainItemType.HIVE
        DbItemType.ANIMAL_HOUSE -> DomainItemType.ANIMAL_HOUSE
        DbItemType.NEST -> DomainItemType.NEST
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

fun Supply.toDomain(): SupplyModel =
    SupplyModel(
        id = id,
        imageUrl = imageUrl,
        name = name,
        description = description,
        amount = amount,
        effectType = effectType.toDomain(),
        effectAmount = effectAmount
    )

fun Expedition.toDomain(): ExpeditionModel =
    ExpeditionModel(
        id = id,
        title = title,
        description = description,
        mapId = mapId,
        animalId = animalId,
        healAmount = healAmount,
        damageAmount = damageAmount,
        status = status.toDomain()
    )

private fun Enemy.toDomain(activeEnemyId: String?): EnemyModel =
    EnemyModel(
        id = id,
        expeditionId = expeditionId,
        name = name,
        description = description,
        imageUrl = imageUrl,
        healAmount = healAmount,
        damageAmount = damageAmount,
        x = x,
        y = y,
        status = when {
            isDefeated -> EnemyStatus.DEFEATED
            id == activeEnemyId -> EnemyStatus.ACTIVE
            else -> EnemyStatus.UNTOUCHED
        }
    )

fun ExpeditionWithData.toDomain(): ExpeditionWithDataModel {
    val sortedEnemies = enemies.sortedWith(compareBy<Enemy>({ it.x }, { it.id }))

    val activeEnemyId = sortedEnemies
        .firstOrNull { !it.isDefeated }
        ?.id

    return ExpeditionWithDataModel(
        id = expedition.id,
        title = expedition.title,
        description = expedition.description,
        mapId = expedition.mapId,
        animalId = expedition.animalId,
        healAmount = expedition.healAmount,
        damageAmount = expedition.damageAmount,
        status = expedition.status.toDomain(),
        enemies = sortedEnemies.map { it.toDomain(activeEnemyId) }
    )
}

fun Furniture.toDomain() : FurnitureModel =
    FurnitureModel(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        roomId = roomId,
        x = x,
        y = y,
        height = height,
        width = width,
        type = type.toDomain()
    )

fun GardenItem.toDomain(): GardenItemModel =
    GardenItemModel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        gardenId = gardenId,
        x = x,
        y = y,
        supplyId = supplyId,
        supplyAmount = supplyAmount,
        itemType = itemType.toDomain(),
        gardenType = gardenType.toDomain(),
        readiness = readiness,
        deleted = deleted
    )

fun Plant.toDomain(): PlantModel =
    PlantModel(
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

fun Pouch.toDomain() : PouchModel =
    PouchModel(
        id = id,
        imageUrl = imageUrl
    )