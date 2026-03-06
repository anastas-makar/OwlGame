package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "expeditions",
    indices = [
        Index(value = ["mapId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["id"],
            childColumns = ["mapId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ])
data class Expedition(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val mapId: String,
    val healAmount: Int,
    val damageAmount: Int
)
