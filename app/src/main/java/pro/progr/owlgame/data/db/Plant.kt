package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant (
    @PrimaryKey
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val gardenId : String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val supplyName : String,
    val supplyAmount : Int,
    val seedAmount : Int,
    val seedImageUrl : String,
    val seedName : String,
    val readiness: Float = 0f
)