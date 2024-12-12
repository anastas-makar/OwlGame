package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "slots")
data class Slot (
    @PrimaryKey
    val id : Int,
    val townId : Long,
    val slotNum : Int, 
    val buildingId : Int
)