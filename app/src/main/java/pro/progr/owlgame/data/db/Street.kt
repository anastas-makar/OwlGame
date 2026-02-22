package pro.progr.owlgame.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "streets",
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class, // замени на свой класс карты
            parentColumns = ["id"],
            childColumns = ["mapId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("mapId"),
        Index(value = ["mapId", "deleted"])
    ]
)
data class Street (
    @PrimaryKey
    val id : String,
    val name : String,
    val mapId : String,

    @ColumnInfo(defaultValue = "0")
    var deleted : Boolean = false
)