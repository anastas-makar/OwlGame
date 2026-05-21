package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.repository.AnimalsRepository
import javax.inject.Inject

class SendAnimalAwayUseCase @Inject constructor(
    private val animalsRepository: AnimalsRepository
) {
    suspend operator fun invoke(animalId: String) {
        animalsRepository.updateAnimalStatus(animalId, AnimalStatus.GONE)
    }
}