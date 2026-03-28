package pro.progr.owlgame.domain

import androidx.room.withTransaction
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.db.AnimalStatus
import pro.progr.owlgame.data.db.EffectType
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.ExpeditionDao
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.model.ExpeditionStatus
import pro.progr.owlgame.domain.model.SelectedSupplyAmount
import javax.inject.Inject

class StartExpeditionUseCase @Inject constructor(
    private val appDatabase: OwlGameDatabase,
    private val suppliesDao: SuppliesDao,
    private val expeditionsDao: ExpeditionDao,
    private val mapsDao: MapDao,
    private val animalDao: AnimalDao
) {
    private val mutex = Mutex()

    suspend operator fun invoke(
        diamondDao: PurchaseInterface,
        mapId: String,
        expeditionId: String,
        animalId: String,
        selectedSupplies: List<SelectedSupplyAmount>,
        extraHeal: Int,
        extraDamage: Int
    ): Result<Int> = mutex.withLock {
        runCatching {
            val expedition = expeditionsDao.getById(expeditionId)
                ?: error("Экспедиция не найдена")

            val animal = animalDao.getAnimalById(animalId)
                ?: error("Животное не найдено")

            check(animal.status == AnimalStatus.PET) {
                "Животное больше нельзя отправить в экспедицию"
            }

            val selectedIds = selectedSupplies.map { it.supplyId }
            val supplies = suppliesDao.getByIds(selectedIds).associateBy { it.id }

            selectedSupplies.forEach { selected ->
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

            val totalHeal = selectedSupplies.sumOf { selected ->
                val supply = supplies.getValue(selected.supplyId)
                if (supply.effectType == EffectType.HEAL) {
                    selected.amount * supply.effectAmount
                } else {
                    0
                }
            } + extraHeal

            val totalDamage = selectedSupplies.sumOf { selected ->
                val supply = supplies.getValue(selected.supplyId)
                if (supply.effectType == EffectType.DAMAGE) {
                    selected.amount * supply.effectAmount
                } else {
                    0
                }
            } + extraDamage

            val diamondCost = extraHeal + extraDamage

            if (diamondCost > 0) {
                val spendResult = diamondDao.spendDiamonds(diamondCost)
                if (spendResult.isFailure || spendResult.getOrNull() != true) {
                    error("Не удалось списать бриллианты")
                }
            }

            appDatabase.withTransaction {
                selectedSupplies.forEach { selected ->
                    val updatedRows = suppliesDao.consume(selected.supplyId, selected.amount)
                    check(updatedRows == 1) {
                        "Не удалось списать припас ${selected.supplyId}"
                    }
                }

                val animalRows = animalDao.updateStatusIfCurrent(
                    animalId = animalId,
                    newStatus = AnimalStatus.EXPEDITION,
                    expectedOldStatus = AnimalStatus.PET
                )
                check(animalRows == 1) {
                    "Животное больше недоступно для экспедиции"
                }

                expeditionsDao.updateStartData(
                    expeditionId = expedition.id,
                    healAmount = totalHeal,
                    damageAmount = totalDamage,
                    animalId = animalId,
                    status = ExpeditionStatus.ACTIVE
                )

                mapsDao.updateType(
                    mapId = mapId,
                    type = MapType.EXPEDITION
                )
            }
        }
    }
}