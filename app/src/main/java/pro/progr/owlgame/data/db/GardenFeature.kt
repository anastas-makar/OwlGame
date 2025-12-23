package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "garden_features")
data class GardenFeature (
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val buildingId : String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val supplyName : String,
    val supplyAmount : Int
)