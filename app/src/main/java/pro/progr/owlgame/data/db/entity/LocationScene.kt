package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "location_scenes",
    indices = [Index("locationId")],
    foreignKeys = [
        ForeignKey(
            entity = Building::class,
            parentColumns = ["id"],
            childColumns = ["locationId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class LocationScene (
    @PrimaryKey
    val id : String,
    val name : String? = null,
    val description: String,
    val imageUrl : String,
    val locationId : String,
    val sceneNumber : Int
)