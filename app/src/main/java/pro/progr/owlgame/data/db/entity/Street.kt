package pro.progr.owlgame.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import pro.progr.owlgame.data.db.model.StreetDirection

@Entity(
    tableName = "streets",
    indices = [Index("mapId")],
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["id"],
            childColumns = ["mapId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Street(
    @PrimaryKey
    val id: String,
    val mapId: String,
    val name: String,
    val direction: StreetDirection = StreetDirection.WEST_TO_EAST,

    @ColumnInfo(defaultValue = "0")
    var deleted : Boolean = false
)