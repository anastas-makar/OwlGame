package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.repository.AnimalTimingRepository
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.BuildingsRepository
import javax.inject.Inject

class GrantBuildingToAnimalUseCase @Inject constructor(
    val buildingsRepository: BuildingsRepository,
    val animalsRepository: AnimalsRepository,
    val animalTimingRepository: AnimalTimingRepository
) {
    suspend operator fun invoke(buildingId: String,
                        animalId: String,
                        newAnimalName: String) {
        animalsRepository.updateAnimalName(animalId = animalId, newAnimalName = newAnimalName)
        buildingsRepository.updateAnimalId(buildingId, animalId)
        animalsRepository.setPet(animalId)
        animalTimingRepository.clearAnimalDayAndId()
    }
}