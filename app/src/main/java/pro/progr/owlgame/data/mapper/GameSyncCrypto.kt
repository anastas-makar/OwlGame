package pro.progr.owlgame.data.mapper

import pro.progr.personalcrypto.EncryptionContext
import pro.progr.personalcrypto.PersonalCrypto

private val OWL_GAME_SYNC_CONTEXT = EncryptionContext(
    module = "owlgame",
    entityType = "sync",
    entityId = "payload",
    fieldName = "value"
)

internal fun PersonalCrypto.encryptGameValue(value: String): String =
    encrypt(value, OWL_GAME_SYNC_CONTEXT)

internal fun PersonalCrypto.decryptGameValue(value: String): String =
    decrypt(value, OWL_GAME_SYNC_CONTEXT)
