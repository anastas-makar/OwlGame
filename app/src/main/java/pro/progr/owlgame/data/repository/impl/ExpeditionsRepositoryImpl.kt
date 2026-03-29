package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.db.AnimalStatus
import pro.progr.owlgame.data.db.EffectType
import pro.progr.owlgame.data.db.ExpeditionDao
import pro.progr.owlgame.data.db.ExpeditionWithData
import pro.progr.owlgame.data.db.ExpeditionWithDataDao
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.model.ExpeditionStatus
import pro.progr.owlgame.data.model.StartExpeditionRequest
import pro.progr.owlgame.data.repository.ExpeditionsRepository
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

    override fun getExpeditionWithData(mapId: String) : Flow<ExpeditionWithData> {
        return expeditionWithDataDao.getExpeditionWithData(mapId)
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

}

