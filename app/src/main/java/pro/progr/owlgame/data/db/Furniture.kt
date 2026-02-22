package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "furniture",
    indices = [Index("roomId")],
    foreignKeys = [
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Furniture (
    @PrimaryKey
    val id : String,
    val name : String,
    val price : Int = 0,
    val imageUrl : String,
    val roomId : String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val height : Float,
    val width : Float,
    val type : FurnitureType
)