package pro.progr.owlgame.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class TownWithData (
    @Embedded
    val town: Town,
    @Relation(
        parentColumn = "id",
        entity = Slot::class,
        entityColumn = "townId"
    )
    val slots : List<Slot>
)
