package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expedition_medals")
data class ExpeditionMedal(
    @PrimaryKey val id: String,
    val animalId: String?,
    val expeditionId: String,
    val mapId: String,
    val title: String,
    val description: String,
    val imageUrl: String
)