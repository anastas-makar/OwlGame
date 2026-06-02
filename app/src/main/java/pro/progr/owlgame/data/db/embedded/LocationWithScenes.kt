package pro.progr.owlgame.data.db.embedded

import androidx.room.Embedded
import androidx.room.Relation
import pro.progr.owlgame.data.db.entity.Location
import pro.progr.owlgame.data.db.entity.LocationScene

data class LocationWithScenes(
    @Embedded val location: Location,

    @Relation(
        parentColumn = "id",
        entityColumn = "locationId"
    )
    val scenes: List<LocationScene>
)