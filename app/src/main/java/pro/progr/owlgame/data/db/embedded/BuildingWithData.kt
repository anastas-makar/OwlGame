package pro.progr.owlgame.data.db.embedded

import androidx.room.Embedded
import androidx.room.Relation
import pro.progr.owlgame.data.db.entity.Animal
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.entity.Garden
import pro.progr.owlgame.data.db.entity.RoomEntity

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
