package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pro.progr.owlgame.data.db.model.AnimalStatus

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey
    val id : String,
    val kind : String,
    val name: String,
    val imagePath: String,
    val status: AnimalStatus,
    val statusExpiresAt: Long? = null
)