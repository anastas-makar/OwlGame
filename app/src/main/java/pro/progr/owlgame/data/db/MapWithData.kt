package pro.progr.owlgame.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class MapWithData (
    @Embedded
    val mapEntity: MapEntity,
    @Relation(
        parentColumn = "id",
        entity = Building::class,
        entityColumn = "mapId"
    )
    val buildings : List<Building>
)
