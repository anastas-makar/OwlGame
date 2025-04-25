package pro.progr.owlgame.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class SlotWithBuilding (
    @Embedded
    val slot: Slot,
    @Relation(
        parentColumn = "buildingId",
        entity = Building::class,
        entityColumn = "id"
    )
    val building: Building
)
