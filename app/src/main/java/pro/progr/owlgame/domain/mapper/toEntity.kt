package pro.progr.owlgame.domain.mapper

import pro.progr.owlgame.data.db.Receipt
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.web.inpouch.ReceiptInPouch
import pro.progr.owlgame.data.web.inpouch.SupplyInPouch

fun SupplyInPouch.toEntity(): Supply =
    Supply(
        id = id,
        imageUrl = imageUrl,
        name = name,
        amount = 0,              // ВАЖНО: с сервера amount игрока не приходит
        effectType = effectType,
        effectAmount = effectAmount
    )

fun ReceiptInPouch.toEntity(): Receipt =
    Receipt(
        id = id,
        resSupplyId = resultSupply.id,
        description = description,
        effectType = effectType,
        effectAmount = effectAmount
    )

fun linkId(receiptId: String, supplyId: String): String =
    "${receiptId}__${supplyId}" // стабильный id для SupplyToReceipt
