package pro.progr.owlgame.data.repository

import androidx.room.withTransaction
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.Receipt
import pro.progr.owlgame.data.db.ReceiptsDao
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.db.SupplyToReceipt
import pro.progr.owlgame.data.db.SupplyToReceiptDao
import javax.inject.Inject

class SupplyToReceiptRepository @Inject constructor(
    private val db: OwlGameDatabase,
    private val suppliesDao: SuppliesDao,
    private val receiptsDao: ReceiptsDao,
    private val supplyToReceiptDao: SupplyToReceiptDao
) {
    suspend fun saveReceipts(
        supplies: List<Supply>,
        receipts: List<Receipt>,
        links: List<SupplyToReceipt>
    ) {

        db.withTransaction {

            suppliesDao.insert(supplies)

            receiptsDao.upsertAll(receipts)

            supplyToReceiptDao.deleteByReceiptIds(receipts.map { it.id })

            supplyToReceiptDao.upsertAll(links)
        }

    }
}