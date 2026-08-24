package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.GameSyncResult

interface GameSyncRepository {
    suspend fun sync(): GameSyncResult
}
