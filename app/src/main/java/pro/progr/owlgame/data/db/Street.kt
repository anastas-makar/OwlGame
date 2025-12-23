package pro.progr.owlgame.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "streets")
data class Street (
    @PrimaryKey
    val id : String,
    val name : String,
    val mapId : String,

    @ColumnInfo(defaultValue = "0")
    var deleted : Boolean = false
)