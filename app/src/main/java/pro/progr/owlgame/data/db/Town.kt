package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "towns")
data class Town (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String, 
    val mapId : String
)