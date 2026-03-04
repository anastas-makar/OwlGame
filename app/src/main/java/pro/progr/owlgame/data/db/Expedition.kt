package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expeditions")
data class Expedition(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val mapId: String,
    val healAmount: Int,
    val damageAmount: Int
)
