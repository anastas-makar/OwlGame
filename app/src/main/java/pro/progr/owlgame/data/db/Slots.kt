package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "slots",
    indices = [Index(value = ["buildingId"], unique = true)])
data class Slot (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val x : Float,
    val y : Float,
    val mapId : String,
    val buildingId : String
)