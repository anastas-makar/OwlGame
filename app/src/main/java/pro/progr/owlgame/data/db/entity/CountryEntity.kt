package pro.progr.owlgame.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey
    val id: String,
    val name: String,
    val rulerAnimalId: String? = null,
    @ColumnInfo(defaultValue = "0")
    val deleted: Boolean = false
)