package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animal_medals")
data class AnimalMedal(
    @PrimaryKey val id: String,
    val animalId: String,
    val expeditionId: String,
    val mapId: String,
    val title: String,
    val description: String,
    val imageUrl: String
)