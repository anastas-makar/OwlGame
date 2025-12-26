package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Книги нужны для того,
 * чтобы объяснить пользователю правила игры
 * Например, книга "О Холодильниках"
 * или "О Животных"
 */
@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val id : String,
    val name: String,
    val imagePath: String
)
