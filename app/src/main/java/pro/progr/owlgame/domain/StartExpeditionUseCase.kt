package pro.progr.owlgame.domain

import androidx.room.withTransaction
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.EffectType
import pro.progr.owlgame.data.db.ExpeditionDao
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.model.ExpeditionStatus
import pro.progr.owlgame.domain.model.SelectedSupplyAmount
import javax.inject.Inject

class StartExpeditionUseCase @Inject constructor(
    private val appDatabase: OwlGameDatabase,
    private val suppliesDao: SuppliesDao,
    private val expeditionsDao: ExpeditionDao,
    private val mapsDao: MapDao
) {
    private val mutex = Mutex()

    suspend operator fun invoke(
        diamondDao: PurchaseInterface,
        mapId: String,
        expeditionId: String,
        selectedSupplies: List<SelectedSupplyAmount>,
        extraHeal: Int,
        extraDamage: Int
    ): Result<Int> = mutex.withLock {
        runCatching {
            require(extraHeal >= 0) { "Heal не может быть отрицательным" }
            require(extraDamage >= 0) { "Damage не может быть отрицательным" }

            val diamondCost = extraHeal + extraDamage

            val selectedIds = selectedSupplies.map { it.supplyId }
            val supplies = suppliesDao.getByIds(selectedIds).associateBy { it.id }

            selectedSupplies.forEach { selected ->
                require(selected.amount > 0) { "Количество припаса должно быть больше нуля" }

                val supply = requireNotNull(supplies[selected.supplyId]) {
                    "Припас не найден"
                }

                check(supply.amount >= selected.amount) {
                    "Недостаточно припаса: ${supply.name}"
                }
            }

            val totalHealFromSupplies = selectedSupplies.sumOf { selected ->
                val supply = supplies.getValue(selected.supplyId)
                if (supply.effectType == EffectType.HEAL) {
                    selected.amount * supply.effectAmount
                } else 0
            }

            val totalDamageFromSupplies = selectedSupplies.sumOf { selected ->
                val supply = supplies.getValue(selected.supplyId)
                if (supply.effectType == EffectType.DAMAGE) {
                    selected.amount * supply.effectAmount
                } else 0
            }

            val totalHeal = totalHealFromSupplies + extraHeal
            val totalDamage = totalDamageFromSupplies + extraDamage

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

                expeditionsDao.updateStartData(
                    expeditionId = expeditionId,
                    healAmount = totalHeal,
                    damageAmount = totalDamage,
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