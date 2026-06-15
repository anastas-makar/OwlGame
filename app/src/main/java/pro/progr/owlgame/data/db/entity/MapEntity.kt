package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import pro.progr.owlgame.data.db.model.MapType

@Entity(
    tableName = "maps",
    indices = [Index("countryId")]
)
data class MapEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imagePath: String,
    val type: MapType,
    val countryId: String? = null
)