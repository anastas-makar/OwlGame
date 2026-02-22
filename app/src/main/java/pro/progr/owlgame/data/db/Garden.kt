package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "gardens",
    indices = [Index("buildingId")],
    foreignKeys = [
        ForeignKey(
            entity = Building::class,
            parentColumns = ["id"],
            childColumns = ["buildingId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Garden (
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val buildingId : String,
    val gardenNumber : Int,
    val gardenType : GardenType
)