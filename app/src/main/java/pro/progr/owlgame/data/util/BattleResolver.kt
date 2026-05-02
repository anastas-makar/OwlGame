package pro.progr.owlgame.data.util

import pro.progr.owlgame.data.db.entity.Enemy
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.db.model.AnimalStatus
import pro.progr.owlgame.data.db.model.MapType
import pro.progr.owlgame.data.model.BattleResolution
import pro.progr.owlgame.data.model.ExpeditionStatus
import javax.inject.Inject

class BattleResolver @Inject constructor() {
    operator fun invoke(
        expedition: Expedition,
        enemies: List<Enemy>,
        availableTicks: Long
    ): BattleResolution {
        var remainingTicks = availableTicks
        var expeditionHeal = expedition.healAmount
        var expeditionDamage = expedition.damageAmount

        val sortedEnemies = enemies
            .sortedWith(compareBy<Enemy>({ it.x }, { it.id }))
            .map { it.copy() }
            .toMutableList()

        for (index in sortedEnemies.indices) {
            val enemy = sortedEnemies[index]

            if (enemy.isDefeated) {
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
                isDefeated = enemyDead
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

        val allDefeated = sortedEnemies.all { it.isDefeated }

        return if (allDefeated) {
            BattleResolution(
                expeditionHeal = expeditionHeal,
                expeditionDamage = expeditionDamage,
                updatedEnemies = sortedEnemies,
                expeditionStatus = ExpeditionStatus.LOOT_AVAILABLE,
                medalForAnimal = true,
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