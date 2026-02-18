package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "buildings",
    indices = [Index(value = ["animalId"], unique = true)])
data class Building (
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val mapId : String? = null,
    val price : Int = 500,
    var animalId: String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: BuildingType = BuildingType.HOUSE
)