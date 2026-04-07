package pro.progr.owlgame.data.db.embedded

import androidx.room.Embedded
import androidx.room.Relation
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.entity.MapEntity

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
