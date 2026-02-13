package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.db.SupplyToReceipt
import pro.progr.owlgame.data.repository.SupplyToReceiptRepository
import pro.progr.owlgame.data.web.inpouch.ReceiptInPouch
import pro.progr.owlgame.domain.mapper.linkId
import pro.progr.owlgame.domain.mapper.toEntity
import javax.inject.Inject

class SaveReceiptsUseСase @Inject constructor(
    private val suplyToReceiptRepository: SupplyToReceiptRepository
) {

    suspend operator fun invoke(receipts: List<ReceiptInPouch>) {
        if (receipts.isEmpty()) return

        // 1) Собираем все Supply (result + ingredients), вставляем только отсутствующие
        val allSupplies: List<Supply> =
            buildList {
                receipts.forEach { r ->
                    add(r.resultSupply.toEntity())
                    r.ingredients.forEach { ing -> add(ing.supplyInPouch.toEntity()) }
                }
            }
                .distinctBy { it.id }

        val receiptEntities = receipts.map { it.toEntity() }

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

        suplyToReceiptRepository.saveReceipts(allSupplies, receiptEntities, links)
    }
}