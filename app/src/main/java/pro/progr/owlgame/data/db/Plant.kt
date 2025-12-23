package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant (
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val buildingId : String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val readiness : Float = 0f
)