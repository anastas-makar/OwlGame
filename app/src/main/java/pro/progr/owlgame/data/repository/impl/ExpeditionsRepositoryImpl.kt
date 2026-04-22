package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.AnimalDao
import pro.progr.owlgame.data.db.dao.ExpeditionDao
import pro.progr.owlgame.data.db.dao.ExpeditionWithDataDao
import pro.progr.owlgame.data.db.dao.MapDao
import pro.progr.owlgame.data.db.dao.SuppliesDao
import pro.progr.owlgame.data.db.model.AnimalStatus
import pro.progr.owlgame.data.db.model.EffectType
import pro.progr.owlgame.data.db.model.MapType
import pro.progr.owlgame.domain.model.MapType as DomainMapType
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.model.ExpeditionStatus
import pro.progr.owlgame.domain.model.ExpeditionStatus as DomainExpeditionStatus
import pro.progr.owlgame.domain.model.BattleResolution
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.domain.model.AnimalStatus as DomainAnimalStatus
import pro.progr.owlgame.domain.model.ExpeditionModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.domain.model.StartExpeditionRequest
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import javax.inject.Inject

class ExpeditionsRepositoryImpl @Inject constructor(
    private val expeditionWithDataDao: ExpeditionWithDataDao,
    private val appDatabase: OwlGameDatabase,
    private val expeditionsDao: ExpeditionDao,
    private val suppliesDao: SuppliesDao,
    private val mapsDao: MapDao,
    private val animalDao: AnimalDao
) : ExpeditionsRepository {

    private val mutex = Mutex()

    override fun getExpeditionWithData(mapId: String) : Flow<ExpeditionWithDataModel> {
        return expeditionWithDataDao.getExpeditionWithData(mapId).map { it.toDomain() }
    }

    override suspend fun startExpedition(
        diamondDao: PurchaseInterface,
        request: StartExpeditionRequest
    ): Result<Unit> = mutex.withLock {
        runCatching {
            require(request.extraHeal >= 0) { "Heal не может быть отрицательным" }
            require(request.extraDamage >= 0) { "Damage не может быть отрицательным" }

            val expedition = expeditionsDao.getById(request.expeditionId)
                ?: error("Экспедиция не найдена")

            val animal = animalDao.getAnimalById(request.animalId)
                ?: error("Животное не найдено")

            check(animal.status == AnimalStatus.PET) {
                "Животное больше нельзя отправить в экспедицию"
            }

            val selectedIds = request.selectedSupplies.map { it.supplyId }
            val supplies = suppliesDao.getByIds(selectedIds).associateBy { it.id }

            request.selectedSupplies.forEach { selected ->
                require(selected.amount > 0) {
                    "Количество припаса должно быть больше нуля"
                }

                val supply = requireNotNull(supplies[selected.supplyId]) {
                    "Припас не найден"
                }

                check(supply.amount >= selected.amount) {
                    "Недостаточно припаса: ${supply.name}"
                }
            }

            val totalHeal = request.selectedSupplies.sumOf { selected ->
                val supply = supplies.getValue(selected.supplyId)
                if (supply.effectType == EffectType.HEAL) {
                    selected.amount * supply.effectAmount
                } else {
                    0
                }
            } + request.extraHeal

            val totalDamage = request.selectedSupplies.sumOf { selected ->
                val supply = supplies.getValue(selected.supplyId)
                if (supply.effectType == EffectType.DAMAGE) {
                    selected.amount * supply.effectAmount
                } else {
                    0
                }
            } + request.extraDamage

            val diamondCost = request.extraHeal + request.extraDamage

            if (diamondCost > 0) {
                val spendResult = diamondDao.spendDiamonds(diamondCost)
                if (spendResult.isFailure || spendResult.getOrNull() != true) {
                    error("Не удалось списать бриллианты")
                }
            }

            appDatabase.withTransaction {
                request.selectedSupplies.forEach { selected ->
                    val updatedRows = suppliesDao.consume(selected.supplyId, selected.amount)
                    check(updatedRows == 1) {
                        "Не удалось списать припас ${selected.supplyId}"
                    }
                }

                val animalRows = animalDao.updateStatusIfCurrent(
                    animalId = request.animalId,
                    newStatus = AnimalStatus.EXPEDITION,
                    expectedOldStatus = AnimalStatus.PET
                )
                check(animalRows == 1) {
                    "Животное больше недоступно для экспедиции"
                }

                val expeditionRows = expeditionsDao.updateStartData(
                    expeditionId = expedition.id,
                    healAmount = totalHeal,
                    damageAmount = totalDamage,
                    animalId = request.animalId,
                    status = ExpeditionStatus.ACTIVE
                )
                check(expeditionRows == 1) {
                    "Не удалось обновить экспедицию"
                }

                val mapRows = mapsDao.updateType(
                    mapId = request.mapId,
                    type = MapType.EXPEDITION
                )
                check(mapRows == 1) {
                    "Не удалось обновить карту"
                }

                Unit
            }
        }
    }

    override suspend fun getById(expeditionId: String) : ExpeditionModel? {
        return expeditionsDao.getById(expeditionId)?.toDomain()
    }

    override suspend fun updateAnimalId(
        expeditionId: String,
        animalId: String?
    ): Int {
        return expeditionsDao.updateAnimalId(expeditionId, animalId)
    }

    override fun resolveBattle(
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
                    expeditionStatus = DomainExpeditionStatus.LOST,
                    mapType = DomainMapType.OCCUPIED,
                    animalStatus = DomainAnimalStatus.FUGITIVE,
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
                expeditionStatus = DomainExpeditionStatus.WON,
                mapType = DomainMapType.FREE,
                animalStatus = DomainAnimalStatus.PET,
                appliedTicks = availableTicks - remainingTicks
            )
        } else {
            BattleResolution(
                expeditionHeal = expeditionHeal,
                expeditionDamage = expeditionDamage,
                updatedEnemies = sortedEnemies,
                expeditionStatus = DomainExpeditionStatus.ACTIVE,
                mapType = DomainMapType.EXPEDITION,
                animalStatus = DomainAnimalStatus.EXPEDITION,
                appliedTicks = availableTicks - remainingTicks
            )
        }
    }

}

