package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.web.inpouch.BuildingInPouch
import pro.progr.owlgame.data.web.inpouch.DiamondsInPouch
import pro.progr.owlgame.data.web.inpouch.EnemyInPouch
import pro.progr.owlgame.data.web.inpouch.ExpeditionInPouch
import pro.progr.owlgame.data.web.inpouch.FurnitureInPouch
import pro.progr.owlgame.data.web.inpouch.GardenInPouch
import pro.progr.owlgame.data.web.inpouch.GardenItemInPouch
import pro.progr.owlgame.data.web.inpouch.InPouch
import pro.progr.owlgame.data.web.inpouch.IngredientInPouch
import pro.progr.owlgame.data.web.inpouch.MapInPouch
import pro.progr.owlgame.data.web.inpouch.PlantInPouch
import pro.progr.owlgame.data.web.inpouch.RecipeInPouch
import pro.progr.owlgame.data.web.inpouch.RoomInPouch
import pro.progr.owlgame.data.web.inpouch.SupplyInPouch
import pro.progr.owlgame.domain.model.BuildingWithDataModel
import pro.progr.owlgame.domain.model.DiamondsModel
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.domain.model.ExpeditionStatus
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.model.GardenItemWithSupplyModel
import pro.progr.owlgame.domain.model.GardenModel
import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.domain.model.IngredientWithSupplyModel
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.model.PlantWithSupplyModel
import pro.progr.owlgame.domain.model.RecipeWithSuppliesModel
import pro.progr.owlgame.domain.model.RoomModel
import pro.progr.owlgame.domain.model.SupplyModel

fun InPouch.toDomain(): InPouchModel =
    InPouchModel(
        buildings = buildings.map { it.toDomain() },
        maps = maps.map { it.toDomain() },
        diamonds = diamonds?.toDomain(),
        gardenItems = gardenItems.map { it.toDomain() },
        plants = plants.map { it.toDomain() },
        furniture = furniture.map { it.toDomain() },
        recipes = recipes.map { it.toDomain() }
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

fun MapInPouch.toDomain(): MapWithDataModel =
    MapWithDataModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        type = type.toDomain(),
        buildings = emptyList(),
        expedition = expedition?.toDomain(mapId = id)
    )

fun ExpeditionInPouch.toDomain(mapId: String): ExpeditionWithDataModel =
    ExpeditionWithDataModel(
        id = id,
        title = title,
        description = description,
        mapId = mapId,
        animalId = null,
        healAmount = 0,
        damageAmount = 0,
        status = ExpeditionStatus.ACTIVE,
        enemies = enemies.map { it.toDomain(expeditionId = id) }
    )

fun EnemyInPouch.toDomain(expeditionId: String): EnemyModel =
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