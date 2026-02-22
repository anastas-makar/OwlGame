package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "garden_items",
    foreignKeys = [
        ForeignKey(
            entity = Garden::class,
            parentColumns = ["id"],
            childColumns = ["gardenId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Supply::class,
            parentColumns = ["id"],
            childColumns = ["supplyId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("gardenId"),
        Index("supplyId"),
        Index(value = ["gardenId", "deleted"])
    ]
)
data class GardenItem (
    @PrimaryKey
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val gardenId : String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val supplyId : String,
    val supplyAmount : Int,
    val itemType : ItemType,
    val gardenType: GardenType,
    val readiness: Float = 0f,
    val deleted: Boolean = false
)