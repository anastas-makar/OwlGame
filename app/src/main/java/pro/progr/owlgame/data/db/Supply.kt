package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supplies")
data class Supply (
    @PrimaryKey
    val id : String,
    val imageUrl : String,
    val name : String
)