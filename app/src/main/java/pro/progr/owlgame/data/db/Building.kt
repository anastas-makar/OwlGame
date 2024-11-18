package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buildings")
data class Building (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val imageUrl : String
)