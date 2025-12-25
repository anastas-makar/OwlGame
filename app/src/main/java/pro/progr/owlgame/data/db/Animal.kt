package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey
    val id : String,
    val kind : String,
    val name: String,
    val imagePath: String,
    val status: AnimalStatus
)
