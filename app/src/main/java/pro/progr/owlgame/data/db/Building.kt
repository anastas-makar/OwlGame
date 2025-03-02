package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buildings")
data class Building (
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val mapId : String? = null,
    val price : Int = 500,
    var animalId: String? = null
)