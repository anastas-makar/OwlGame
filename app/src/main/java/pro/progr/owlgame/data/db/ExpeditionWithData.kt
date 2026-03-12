package pro.progr.owlgame.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class ExpeditionWithData (
    @Embedded
    val expedition: Expedition,
    @Relation(
        parentColumn = "id",
        entity = Enemy::class,
        entityColumn = "expeditionId"
    )
    val enemies : List<Enemy>
)
