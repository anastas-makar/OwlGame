package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import pro.progr.owlgame.data.model.EnemyStatus

@Entity(
    tableName = "enemies",
    indices = [
        Index(value = ["expeditionId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Expedition::class,
            parentColumns = ["id"],
            childColumns = ["expeditionId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Enemy (
    @PrimaryKey
    val id: String,
    val expeditionId: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val healAmount: Int,
    val damageAmount: Int,
    val x: Float,
    val y: Float,
    val status: EnemyStatus = EnemyStatus.UNDEFEATED
)
