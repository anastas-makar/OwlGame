package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.BattleResolution
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.domain.model.ExpeditionModel
import pro.progr.owlgame.domain.model.ExpeditionStatus
import pro.progr.owlgame.domain.model.MapType

class ResolveBattleUseCase {
    operator fun invoke(
        expedition: ExpeditionModel,
        enemies: List<EnemyModel>,
        availableTicks: Long
    ): BattleResolution {
        var remainingTicks = availableTicks
        var expeditionHeal = expedition.healAmount
        var expeditionDamage = expedition.damageAmount

        val sortedEnemies = enemies
            .sortedWith(compareBy<EnemyModel>({ it.x }, { it.id }))
            .map { it.copy() }
            .toMutableList()

        for (index in sortedEnemies.indices) {
            val enemy = sortedEnemies[index]

            if (enemy.status == EnemyStatus.DEFEATED) {
                continue
            }

            if (remainingTicks <= 0) {
                break
            }

            var enemyHeal = enemy.healAmount
            var enemyDamage = enemy.damageAmount

            val expeditionLimit = minOf(expeditionHeal, expeditionDamage)
            val enemyLimit = minOf(enemyHeal, enemyDamage)

            val duelTicks = minOf(
                remainingTicks,
                expeditionLimit.toLong(),
                enemyLimit.toLong()
            )

            if (duelTicks <= 0L) {
                break
            }

            expeditionHeal -= duelTicks.toInt()
            expeditionDamage -= duelTicks.toInt()
            enemyHeal -= duelTicks.toInt()
            enemyDamage -= duelTicks.toInt()
            remainingTicks -= duelTicks

            val expeditionDead = expeditionHeal <= 0 || expeditionDamage <= 0
            val enemyDead = enemyHeal <= 0 || enemyDamage <= 0

            sortedEnemies[index] = enemy.copy(
                healAmount = enemyHeal.coerceAtLeast(0),
                damageAmount = enemyDamage.coerceAtLeast(0),
                status = if (enemyDead) EnemyStatus.DEFEATED else enemy.status
            )

            if (expeditionDead) {
                return BattleResolution(
                    expeditionHeal = expeditionHeal.coerceAtLeast(0),
                    expeditionDamage = expeditionDamage.coerceAtLeast(0),
                    updatedEnemies = sortedEnemies,
                    expeditionStatus = ExpeditionStatus.LOST,
                    mapType = MapType.OCCUPIED,
                    animalStatus = AnimalStatus.FUGITIVE,
                    appliedTicks = availableTicks - remainingTicks
                )
            }

            if (!enemyDead) {
                break
            }
        }

        val allDefeated = sortedEnemies.all { it.status == EnemyStatus.DEFEATED }

        return if (allDefeated) {
            BattleResolution(
                expeditionHeal = expeditionHeal,
                expeditionDamage = expeditionDamage,
                updatedEnemies = sortedEnemies,
                expeditionStatus = ExpeditionStatus.WON,
                mapType = MapType.FREE,
                animalStatus = AnimalStatus.PET,
                appliedTicks = availableTicks - remainingTicks
            )
        } else {
            BattleResolution(
                expeditionHeal = expeditionHeal,
                expeditionDamage = expeditionDamage,
                updatedEnemies = sortedEnemies,
                expeditionStatus = ExpeditionStatus.ACTIVE,
                mapType = MapType.EXPEDITION,
                animalStatus = AnimalStatus.EXPEDITION,
                appliedTicks = availableTicks - remainingTicks
            )
        }
    }
}