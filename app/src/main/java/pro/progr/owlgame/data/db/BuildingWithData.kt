package pro.progr.owlgame.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class BuildingWithData(
    @Embedded val building: Building,

    @Relation(
        parentColumn = "animalId",
        entityColumn = "id",
        entity = Animal::class
    )
    val animal: Animal?,

    @Relation(
        parentColumn = "id",
        entityColumn = "buildingId",
        entity = RoomEntity::class
    )
    val rooms: List<RoomEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "buildingId",
        entity = Garden::class
    )
    val gardens: List<Garden>
)
