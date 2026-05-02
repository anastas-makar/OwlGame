package pro.progr.owlgame.data.db.embedded

import androidx.room.Embedded
import androidx.room.Relation
import pro.progr.owlgame.data.db.entity.Enemy
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.db.entity.ExpeditionMedal

data class ExpeditionWithData (
    @Embedded
    val expedition: Expedition,
    @Relation(
        parentColumn = "id",
        entity = Enemy::class,
        entityColumn = "expeditionId"
    )
    val enemies : List<Enemy>,
    @Relation(
        parentColumn = "id",
        entity = ExpeditionMedal::class,
        entityColumn = "expeditionId"
    )
    val medal: ExpeditionMedal
)
