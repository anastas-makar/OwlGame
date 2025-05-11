package pro.progr.owlgame.domain

import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.BuildingsRepository
import javax.inject.Inject

class GrantBuildingToAnimalUseCase @Inject constructor(
    val buildingsRepository: BuildingsRepository,
    val animalsRepository: AnimalsRepository
) {
    operator fun invoke(buildingId: String, animalId: String) {
        buildingsRepository.updateAnimalId(buildingId, animalId)
        animalsRepository.setPet(animalId)
    }
}