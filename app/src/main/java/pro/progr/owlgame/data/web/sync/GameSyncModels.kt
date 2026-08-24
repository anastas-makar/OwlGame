package pro.progr.owlgame.data.web.sync

import pro.progr.owlgame.data.db.model.AnimalStatus
import pro.progr.owlgame.data.db.model.BuildingType
import pro.progr.owlgame.data.db.model.EffectType
import pro.progr.owlgame.data.db.model.FurnitureType
import pro.progr.owlgame.data.db.model.GardenType
import pro.progr.owlgame.data.db.model.ItemType
import pro.progr.owlgame.data.db.model.MapType
import pro.progr.owlgame.data.db.model.StreetDirection
import pro.progr.owlgame.data.model.ExpeditionStatus
import pro.progr.owlgame.data.model.LocationType

data class GameSyncMetaData(
    val deviceId: String,
    val dbVersion: Int
)

data class GameBackupRequest(
    val syncMetaData: GameSyncMetaData,
    val data: GameSyncData
)

data class GameBackupResponse(
    val accepted: Boolean
)

data class GameRestoreRequest(
    val syncMetaData: GameSyncMetaData
)

data class GameRestoreResponse(
    val hasBackup: Boolean,
    val sourceDeviceId: String? = null,
    val data: GameSyncData = GameSyncData()
)

data class GameSyncData(
    val animals: List<AnimalSyncDto> = emptyList(),
    val buildings: List<BuildingSyncDto> = emptyList(),
    val countries: List<CountrySyncDto> = emptyList(),
    val enemies: List<EnemySyncDto> = emptyList(),
    val expeditions: List<ExpeditionSyncDto> = emptyList(),
    val expeditionMedals: List<ExpeditionMedalSyncDto> = emptyList(),
    val furniture: List<FurnitureSyncDto> = emptyList(),
    val gardens: List<GardenSyncDto> = emptyList(),
    val gardenItems: List<GardenItemSyncDto> = emptyList(),
    val locations: List<LocationSyncDto> = emptyList(),
    val locationScenes: List<LocationSceneSyncDto> = emptyList(),
    val maps: List<MapSyncDto> = emptyList(),
    val plants: List<PlantSyncDto> = emptyList(),
    val recipes: List<RecipeSyncDto> = emptyList(),
    val rooms: List<RoomSyncDto> = emptyList(),
    val streets: List<StreetSyncDto> = emptyList(),
    val supplies: List<SupplySyncDto> = emptyList(),
    val supplyToRecipes: List<SupplyToRecipeSyncDto> = emptyList()
)

data class AnimalSyncDto(
    val id: String,
    val kind: String,
    val name: String?,
    val initialDisplayName: String,
    val status: AnimalStatus,
    val statusExpiresAt: Long?,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class BuildingSyncDto(
    val id: String,
    val name: String,
    val mapId: String?,
    val price: Int,
    val animalId: String?,
    val streetId: String?,
    val x: Float,
    val y: Float,
    val type: BuildingType,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class CountrySyncDto(
    val id: String,
    val name: String,
    val rulerAnimalId: String?,
    val deleted: Boolean
)

data class EnemySyncDto(
    val id: String,
    val expeditionId: String,
    val name: String,
    val description: String,
    val healAmount: Int,
    val damageAmount: Int,
    val maxHealAmount: Int,
    val maxDamageAmount: Int,
    val x: Float,
    val y: Float,
    val isDefeated: Boolean,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class ExpeditionSyncDto(
    val id: String,
    val title: String,
    val description: String,
    val mapId: String,
    val animalId: String?,
    val healAmount: Int,
    val damageAmount: Int,
    val maxHealAmount: Int,
    val maxDamageAmount: Int,
    val lastBattleUpdateTime: Long?,
    val status: ExpeditionStatus
)

data class ExpeditionMedalSyncDto(
    val id: String,
    val animalId: String?,
    val expeditionId: String,
    val mapId: String,
    val title: String,
    val description: String,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class FurnitureSyncDto(
    val id: String,
    val name: String,
    val price: Int,
    val roomId: String?,
    val x: Float,
    val y: Float,
    val height: Float,
    val width: Float,
    val type: FurnitureType,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class GardenSyncDto(
    val id: String,
    val name: String,
    val buildingId: String,
    val gardenNumber: Int,
    val gardenType: GardenType,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class GardenItemSyncDto(
    val id: String,
    val name: String,
    val description: String,
    val gardenId: String?,
    val x: Float,
    val y: Float,
    val supplyId: String,
    val supplyAmount: Int,
    val itemType: ItemType,
    val gardenType: GardenType,
    val readiness: Float,
    val deleted: Boolean,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class LocationSyncDto(
    val id: String,
    val name: String,
    val description: String,
    val mapId: String?,
    val price: Int,
    val x: Float,
    val y: Float,
    val type: LocationType,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class LocationSceneSyncDto(
    val id: String,
    val name: String?,
    val description: String,
    val locationId: String,
    val sceneNumber: Int,
    val questId: String?,
    val questButtonText: String?,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class MapSyncDto(
    val id: String,
    val name: String,
    val type: MapType,
    val countryId: String?,
    val mayorAnimalId: String?,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class PlantSyncDto(
    val id: String,
    val name: String,
    val description: String,
    val gardenId: String?,
    val x: Float,
    val y: Float,
    val supplyId: String,
    val supplyAmount: Int,
    val seedAmount: Int,
    val readiness: Float,
    val deleted: Boolean,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class RecipeSyncDto(
    val id: String,
    val resSupplyId: String,
    val description: String,
    val templateId: String
)

data class RoomSyncDto(
    val id: String,
    val name: String,
    val buildingId: String,
    val roomNumber: Int,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class StreetSyncDto(
    val id: String,
    val mapId: String,
    val name: String,
    val direction: StreetDirection,
    val deleted: Boolean
)

data class SupplySyncDto(
    val id: String,
    val name: String,
    val description: String,
    val amount: Int,
    val effectType: EffectType,
    val effectAmount: Int,
    val templateId: String,
    val imageKey: String,
    val imageUrl: String? = null
)

data class SupplyToRecipeSyncDto(
    val id: String,
    val supplyId: String,
    val recipeId: String,
    val amount: Int,
    val deleted: Boolean
)
