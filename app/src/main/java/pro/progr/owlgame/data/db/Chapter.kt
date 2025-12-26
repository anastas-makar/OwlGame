package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey
    val id : String,
    val text : String,
    val imageUrl: String?,
    val bookId: String
)
