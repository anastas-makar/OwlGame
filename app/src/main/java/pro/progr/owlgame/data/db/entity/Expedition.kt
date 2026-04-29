package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import pro.progr.owlgame.data.model.ExpeditionStatus

@Entity(
    tableName = "expeditions",
    indices = [
        Index(value = ["mapId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["id"],
            childColumns = ["mapId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Animal::class,
            parentColumns = ["id"],
            childColumns = ["animalId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Expedition(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val mapId: String,
    val animalId: String?,
    val healAmount: Int,
    val damageAmount: Int,
    val maxHealAmount: Int,
    val maxDamageAmount: Int,
    val lastBattleUpdateTime: Long? = null,
    val status: ExpeditionStatus = ExpeditionStatus.ACTIVE
)
