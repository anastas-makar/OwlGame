package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import javax.inject.Inject

class ResolveExpeditionProgressUseCase @Inject constructor(
    private val expeditionsRepository: ExpeditionsRepository
) {
    operator fun invoke() {

    }
}