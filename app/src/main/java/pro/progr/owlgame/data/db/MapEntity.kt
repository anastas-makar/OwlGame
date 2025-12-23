package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maps")
data class MapEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imagePath: String,
    val type: MapType = MapType.FREE
)