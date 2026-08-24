package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.db.entity.*
import pro.progr.owlgame.data.web.sync.*

fun Animal.toSyncDto() = AnimalSyncDto(
    id, kind, name, initialDisplayName, status, statusExpiresAt,
    templateId, imageKey
)

fun AnimalSyncDto.toEntity(localImagePath: String) = Animal(
    id = id,
    kind = kind,
    name = name,
    initialDisplayName = initialDisplayName,
    imagePath = localImagePath,
    status = status,
    statusExpiresAt = statusExpiresAt,
    templateId = templateId,
    imageKey = imageKey
)

fun Building.toSyncDto() = BuildingSyncDto(
    id, name, mapId, price, animalId, streetId, x, y, type,
    templateId, imageKey
)

fun BuildingSyncDto.toEntity(localImagePath: String) = Building(
    id = id,
    name = name,
    imageUrl = localImagePath,
    mapId = mapId,
    price = price,
    animalId = animalId,
    streetId = streetId,
    x = x,
    y = y,
    type = type,
    templateId = templateId,
    imageKey = imageKey
)

fun Country.toSyncDto() = CountrySyncDto(id, name, rulerAnimalId, deleted)
fun CountrySyncDto.toEntity() = Country(id, name, rulerAnimalId, deleted)

fun Enemy.toSyncDto() = EnemySyncDto(
    id, expeditionId, name, description, healAmount, damageAmount,
    maxHealAmount, maxDamageAmount, x, y, isDefeated, templateId, imageKey
)

fun EnemySyncDto.toEntity(localImagePath: String) = Enemy(
    id = id,
    expeditionId = expeditionId,
    name = name,
    description = description,
    imageUrl = localImagePath,
    healAmount = healAmount,
    damageAmount = damageAmount,
    maxHealAmount = maxHealAmount,
    maxDamageAmount = maxDamageAmount,
    x = x,
    y = y,
    isDefeated = isDefeated,
    templateId = templateId,
    imageKey = imageKey
)

fun Expedition.toSyncDto() = ExpeditionSyncDto(
    id, title, description, mapId, animalId, healAmount, damageAmount,
    maxHealAmount, maxDamageAmount, lastBattleUpdateTime, status
)

fun ExpeditionSyncDto.toEntity() = Expedition(
    id, title, description, mapId, animalId, healAmount, damageAmount,
    maxHealAmount, maxDamageAmount, lastBattleUpdateTime, status
)

fun ExpeditionMedal.toSyncDto() = ExpeditionMedalSyncDto(
    id, animalId, expeditionId, mapId, title, description, templateId, imageKey
)

fun ExpeditionMedalSyncDto.toEntity(localImagePath: String) = ExpeditionMedal(
    id = id,
    animalId = animalId,
    expeditionId = expeditionId,
    mapId = mapId,
    title = title,
    description = description,
    imageUrl = localImagePath,
    templateId = templateId,
    imageKey = imageKey
)

fun Furniture.toSyncDto() = FurnitureSyncDto(
    id, name, price, roomId, x, y, height, width, type, templateId, imageKey
)

fun FurnitureSyncDto.toEntity(localImagePath: String) = Furniture(
    id = id,
    name = name,
    price = price,
    imageUrl = localImagePath,
    roomId = roomId,
    x = x,
    y = y,
    height = height,
    width = width,
    type = type,
    templateId = templateId,
    imageKey = imageKey
)

fun Garden.toSyncDto() = GardenSyncDto(
    id, name, buildingId, gardenNumber, gardenType, templateId, imageKey
)

fun GardenSyncDto.toEntity(localImagePath: String) = Garden(
    id = id,
    name = name,
    imageUrl = localImagePath,
    buildingId = buildingId,
    gardenNumber = gardenNumber,
    gardenType = gardenType,
    templateId = templateId,
    imageKey = imageKey
)

fun GardenItem.toSyncDto() = GardenItemSyncDto(
    id, name, description, gardenId, x, y, supplyId, supplyAmount,
    itemType, gardenType, readiness, deleted, templateId, imageKey
)

fun GardenItemSyncDto.toEntity(localImagePath: String) = GardenItem(
    id = id,
    name = name,
    description = description,
    imageUrl = localImagePath,
    gardenId = gardenId,
    x = x,
    y = y,
    supplyId = supplyId,
    supplyAmount = supplyAmount,
    itemType = itemType,
    gardenType = gardenType,
    readiness = readiness,
    deleted = deleted,
    templateId = templateId,
    imageKey = imageKey
)

fun Location.toSyncDto() = LocationSyncDto(
    id, name, description, mapId, price, x, y, type, templateId, imageKey
)

fun LocationSyncDto.toEntity(localImagePath: String) = Location(
    id = id,
    name = name,
    description = description,
    imageUrl = localImagePath,
    mapId = mapId,
    price = price,
    x = x,
    y = y,
    type = type,
    templateId = templateId,
    imageKey = imageKey
)

fun LocationScene.toSyncDto() = LocationSceneSyncDto(
    id, name, description, locationId, sceneNumber, questId, questButtonText,
    templateId, imageKey
)

fun LocationSceneSyncDto.toEntity(localImagePath: String) = LocationScene(
    id = id,
    name = name,
    description = description,
    imageUrl = localImagePath,
    locationId = locationId,
    sceneNumber = sceneNumber,
    questId = questId,
    questButtonText = questButtonText,
    templateId = templateId,
    imageKey = imageKey
)

fun MapEntity.toSyncDto() = MapSyncDto(
    id, name, type, countryId, mayorAnimalId, templateId, imageKey
)

fun MapSyncDto.toEntity(localImagePath: String) = MapEntity(
    id = id,
    name = name,
    imagePath = localImagePath,
    type = type,
    countryId = countryId,
    mayorAnimalId = mayorAnimalId,
    templateId = templateId,
    imageKey = imageKey
)

fun Plant.toSyncDto() = PlantSyncDto(
    id, name, description, gardenId, x, y, supplyId, supplyAmount, seedAmount,
    readiness, deleted, templateId, imageKey
)

fun PlantSyncDto.toEntity(localImagePath: String) = Plant(
    id = id,
    name = name,
    description = description,
    imageUrl = localImagePath,
    gardenId = gardenId,
    x = x,
    y = y,
    supplyId = supplyId,
    supplyAmount = supplyAmount,
    seedAmount = seedAmount,
    readiness = readiness,
    deleted = deleted,
    templateId = templateId,
    imageKey = imageKey
)

fun Recipe.toSyncDto() = RecipeSyncDto(id, resSupplyId, description, templateId)
fun RecipeSyncDto.toEntity() = Recipe(id, resSupplyId, description, templateId)

fun RoomEntity.toSyncDto() = RoomSyncDto(
    id, name, buildingId, roomNumber, templateId, imageKey
)

fun RoomSyncDto.toEntity(localImagePath: String) = RoomEntity(
    id = id,
    name = name,
    imageUrl = localImagePath,
    buildingId = buildingId,
    roomNumber = roomNumber,
    templateId = templateId,
    imageKey = imageKey
)

fun Street.toSyncDto() = StreetSyncDto(id, mapId, name, direction, deleted)
fun StreetSyncDto.toEntity() = Street(id, mapId, name, direction, deleted)

fun Supply.toSyncDto() = SupplySyncDto(
    id, name, description, amount, effectType, effectAmount, templateId, imageKey
)

fun SupplySyncDto.toEntity(localImagePath: String) = Supply(
    id = id,
    imageUrl = localImagePath,
    name = name,
    description = description,
    amount = amount,
    effectType = effectType,
    effectAmount = effectAmount,
    templateId = templateId,
    imageKey = imageKey
)

fun SupplyToRecipe.toSyncDto() = SupplyToRecipeSyncDto(
    id, supplyId, recipeId, amount, deleted
)

fun SupplyToRecipeSyncDto.toEntity() = SupplyToRecipe(
    id, supplyId, recipeId, amount, deleted
)
