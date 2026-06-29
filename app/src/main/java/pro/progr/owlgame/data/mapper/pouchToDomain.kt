package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.web.pouchitems.BuildingInPouch
import pro.progr.owlgame.data.web.pouchitems.DiamondsInPouch
import pro.progr.owlgame.data.web.pouchitems.EnemyInPouch
import pro.progr.owlgame.data.web.pouchitems.ExpeditionInPouch
import pro.progr.owlgame.data.web.pouchitems.ExpeditionMedalInPouch
import pro.progr.owlgame.data.web.pouchitems.FurnitureInPouch
import pro.progr.owlgame.data.web.pouchitems.GardenInPouch
import pro.progr.owlgame.data.web.pouchitems.GardenItemInPouch
import pro.progr.owlgame.data.web.pouchitems.PouchItemsDto
import pro.progr.owlgame.data.web.pouchitems.IngredientInPouch
import pro.progr.owlgame.data.web.pouchitems.LocationInPouch
import pro.progr.owlgame.data.web.pouchitems.LocationSceneInPouch
import pro.progr.owlgame.data.web.pouchitems.MapInPouch
import pro.progr.owlgame.data.web.pouchitems.PlantInPouch
import pro.progr.owlgame.data.web.pouchitems.RecipeInPouch
import pro.progr.owlgame.data.web.pouchitems.RoomInPouch
import pro.progr.owlgame.data.web.pouchitems.SupplyInPouch
import pro.progr.owlgame.domain.model.BuildingWithDataModel
import pro.progr.owlgame.domain.model.DiamondsModel
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.domain.model.ExpeditionMedalModel
import pro.progr.owlgame.domain.model.ExpeditionStatus
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.model.GardenItemWithSupplyModel
import pro.progr.owlgame.domain.model.GardenModel
import pro.progr.owlgame.domain.model.PouchItemsModel
import pro.progr.owlgame.domain.model.IngredientWithSupplyModel
import pro.progr.owlgame.domain.model.LocationSceneModel
import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.model.PlantWithSupplyModel
import pro.progr.owlgame.domain.model.RecipeWithSuppliesModel
import pro.progr.owlgame.domain.model.RoomModel
import pro.progr.owlgame.domain.model.SupplyModel

fun PouchItemsDto.toDomain(): PouchItemsModel =
    PouchItemsModel(
        buildings = buildings.orEmpty().map { it.toDomain() },
        maps = maps.orEmpty().map { it.toDomain() },
        diamonds = diamonds?.toDomain(),
        gardenItems = gardenItems.orEmpty().map { it.toDomain() },
        plants = plants.orEmpty().map { it.toDomain() },
        furniture = furniture.orEmpty().map { it.toDomain() },
        recipes = recipes.orEmpty().map { it.toDomain() },
        locations = locations.orEmpty().map { it.toDomain(null) }
    )

fun BuildingInPouch.toDomain(): BuildingWithDataModel =
    BuildingWithDataModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        mapId = null,
        price = cost,
        x = 0f,
        y = 0f,
        type = type.toDomain(),
        animal = null,
        rooms = rooms.map { it.toDomain(buildingId = id) },
        gardens = gardens.map { it.toDomain(buildingId = id) }
    )

fun RoomInPouch.toDomain(buildingId: String): RoomModel =
    RoomModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        buildingId = buildingId,
        roomNumber = roomNumber
    )

fun GardenInPouch.toDomain(buildingId: String): GardenModel =
    GardenModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        buildingId = buildingId,
        gardenNumber = gardenNumber,
        gardenType = gardenType.toDomain()
    )

fun LocationSceneInPouch.toDomain(locationId: String) : LocationSceneModel =
    LocationSceneModel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        locationId = locationId,
        sceneNumber = sceneNumber
    )

fun LocationInPouch.toDomain(mapId: String?): LocationWithScenesModel =
    LocationWithScenesModel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        mapId = mapId,
        price = price,
        x = x,
        y = y,
        type = type.toDomain(),
        scenes = scenes.map { it.toDomain(id) }
    )

fun MapInPouch.toDomain(): MapWithDataModel =
    MapWithDataModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        type = type.toDomain(),
        countryId = null,
        mayorAnimalId = null,
        buildings = emptyList(),
        expedition = expedition?.toDomain(mapId = id),
        locations = locations.orEmpty().map { it.toDomain(id) }
    )

fun ExpeditionMedalInPouch.toDomain(mapId: String, expeditionId: String, animalId: String?): ExpeditionMedalModel =
    ExpeditionMedalModel (
        id = id,
        mapId = mapId,
        expeditionId = expeditionId,
        animalId = animalId,
        title = title,
        description = description,
        imageUrl = imageUrl
    )

fun ExpeditionInPouch.toDomain(mapId: String): ExpeditionWithDataModel =
    ExpeditionWithDataModel(
        id = id,
        title = title,
        description = description,
        mapId = mapId,
        animalId = null,
        healAmount = 0,
        maxHealAmount = 0,
        damageAmount = 0,
        maxDamageAmount = 0,
        status = ExpeditionStatus.ACTIVE,
        enemies = enemies.map { it.toDomain(expeditionId = id) },
        medal = medal.toDomain(mapId = mapId, expeditionId = id, animalId = null)
    )

fun EnemyInPouch.toDomain(expeditionId: String): EnemyModel =
    EnemyModel(
        id = id,
        expeditionId = expeditionId,
        name = name,
        description = description,
        imageUrl = imageUrl,
        healAmount = healAmount,
        maxHealAmount = healAmount,
        damageAmount = damageAmount,
        maxDamageAmount = damageAmount,
        x = x,
        y = y,
        status = EnemyStatus.UNTOUCHED
    )

fun DiamondsInPouch.toDomain(): DiamondsModel =
    DiamondsModel(
        amount = amount
    )

fun GardenItemInPouch.toDomain(): GardenItemWithSupplyModel =
    GardenItemWithSupplyModel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        supply = supply.toDomain(),
        supplyAmount = supplyAmount,
        itemType = itemType.toDomain(),
        gardenType = gardenType.toDomain()
    )

fun SupplyInPouch.toDomain(): SupplyModel =
    SupplyModel(
        id = id,
        imageUrl = imageUrl,
        name = name,
        description = description,
        amount = 0,
        effectType = effectType.toDomain(),
        effectAmount = effectAmount
    )

fun PlantInPouch.toDomain(): PlantWithSupplyModel =
    PlantWithSupplyModel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        supply = supply.toDomain(),
        supplyAmount = supplyAmount,
        seedAmount = seedAmount
    )

fun FurnitureInPouch.toDomain(): FurnitureModel =
    FurnitureModel(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        roomId = null,
        x = 0f,
        y = 0f,
        height = height,
        width = width,
        type = type.toDomain()
    )

fun RecipeInPouch.toDomain(): RecipeWithSuppliesModel =
    RecipeWithSuppliesModel(
        recipeId = id,
        resultSupply = resultSupply.toDomain(),
        resultName = resultSupply.name,
        resultImageUrl = resultSupply.imageUrl,
        description = description,
        ingredients = ingredients.map { it.toDomain() },
        craftable = false
    )

fun IngredientInPouch.toDomain(): IngredientWithSupplyModel =
    IngredientWithSupplyModel(
        supplyModel = supplyInPouch.toDomain(),
        amount = amount
    )