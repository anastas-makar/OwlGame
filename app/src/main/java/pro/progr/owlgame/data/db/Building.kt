package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buildings")
data class Building (
    @PrimaryKey(autoGenerate = true)
    val id : String,
    val name : String,
    val imageUrl : String,
    val mapId : String? = null
)