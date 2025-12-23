package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Furniture (
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val roomId : String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val height : Float = 0f,
    val width : Float = 0f,
    val type : FurnitureType
)