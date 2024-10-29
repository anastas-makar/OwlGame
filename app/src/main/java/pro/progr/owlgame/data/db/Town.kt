package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "towns")
data class Town (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String, 
    val mapId : String
)