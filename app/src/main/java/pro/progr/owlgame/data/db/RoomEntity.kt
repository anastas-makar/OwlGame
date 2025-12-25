package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity (
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val buildingId : String,
    val roomNumber : Int
)