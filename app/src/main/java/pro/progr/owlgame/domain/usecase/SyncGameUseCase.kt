package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.GameSyncResult
import pro.progr.owlgame.domain.repository.GameSyncRepository
import javax.inject.Inject

class SyncGameUseCase @Inject constructor(
    private val gameSyncRepository: GameSyncRepository
) {
    suspend operator fun invoke(): GameSyncResult = gameSyncRepository.sync()
}
