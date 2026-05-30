package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import pro.progr.owlgame.data.model.LocationType

@Entity(
    tableName = "locations",
    indices = [
        Index(value = ["mapId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["id"],
            childColumns = ["mapId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Location (
    @PrimaryKey
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val mapId : String? = null,
    val price : Int = 0,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: LocationType
)