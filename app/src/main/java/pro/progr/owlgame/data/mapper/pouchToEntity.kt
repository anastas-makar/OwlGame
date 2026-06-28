package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.db.entity.Enemy
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.db.entity.ExpeditionMedal
import pro.progr.owlgame.data.model.ExpeditionStatus
import pro.progr.owlgame.data.web.pouchitems.EnemyInPouch
import pro.progr.owlgame.data.web.pouchitems.ExpeditionInPouch
import pro.progr.owlgame.data.web.pouchitems.ExpeditionMedalInPouch

fun ExpeditionInPouch.toEntity(mapId: String): Expedition =
    Expedition(
        id = id,
        title = title,
        description = description,
        mapId = mapId,
        animalId = null,
        healAmount = 0,
        damageAmount = 0,
        maxHealAmount = 0,
        maxDamageAmount = 0,
        lastBattleUpdateTime = null,
        status = ExpeditionStatus.ACTIVE
    )

fun EnemyInPouch.toEntity(expeditionId: String): Enemy =
    Enemy(
        id = id,
        expeditionId = expeditionId,
        name = name,
        description = description,
        imageUrl = imageUrl,
        healAmount = healAmount,
        damageAmount = damageAmount,
        maxHealAmount = healAmount,
        maxDamageAmount = damageAmount,
        x = x,
        y = y,
        isDefeated = false
    )

fun ExpeditionMedalInPouch.toEntity(
    mapId: String,
    expeditionId: String
): ExpeditionMedal =
    ExpeditionMedal(
        id = id,
        expeditionId = expeditionId,
        mapId = mapId,
        title = title,
        description = description,
        imageUrl = imageUrl,
        animalId = null
    )