package pro.progr.owlgame.domain

import androidx.room.withTransaction
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.ReceiptsDao
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.db.SupplyToReceipt
import pro.progr.owlgame.data.db.SupplyToReceiptDao
import pro.progr.owlgame.data.web.inpouch.ReceiptInPouch
import pro.progr.owlgame.domain.mapper.linkId
import pro.progr.owlgame.domain.mapper.toEntity
import javax.inject.Inject

class SaveReceiptsUsecase @Inject constructor(
    private val db: OwlGameDatabase,
    private val suppliesDao: SuppliesDao,
    private val receiptsDao: ReceiptsDao,
    private val supplyToReceiptDao: SupplyToReceiptDao
) {

    suspend fun saveFromServer(receipts: List<ReceiptInPouch>) {
        if (receipts.isEmpty()) return

        db.withTransaction {
            // 1) Собираем все Supply (result + ingredients), вставляем только отсутствующие
            val allSupplies: List<Supply> =
                buildList {
                    receipts.forEach { r ->
                        add(r.resultSupply.toEntity())
                        r.ingredients.forEach { ing -> add(ing.supplyInPouch.toEntity()) }
                    }
                }
                    .distinctBy { it.id }

            suppliesDao.insert(allSupplies) // IGNORE уже есть у вас

            // 2) Upsert рецептов
            receiptsDao.upsertAll(receipts.map { it.toEntity() })

            // 3) Пересоздаём связки supply_to_receipt (полный снимок ингредиентов)
            val receiptIds = receipts.map { it.id }
            supplyToReceiptDao.deleteByReceiptIds(receiptIds)

            val links: List<SupplyToReceipt> =
                receipts.flatMap { r ->
                    // на всякий случай: если один и тот же supply попался несколько раз — суммируем
                    r.ingredients
                        .groupBy { it.supplyInPouch.id }
                        .map { (supplyId, items) ->
                            SupplyToReceipt(
                                id = linkId(r.id, supplyId),
                                supplyId = supplyId,
                                receiptId = r.id,
                                amount = items.sumOf { it.amount }
                            )
                        }
                }

            supplyToReceiptDao.upsertAll(links)
        }
    }
}