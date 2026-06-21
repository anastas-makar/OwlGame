package pro.progr.owlgame.presentation.ui.map

import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.MapWithDataModel

fun MapWithDataModel.findMayorAnimal(): AnimalModel? {
    val mayorId = mayorAnimalId ?: return null

    return buildings
        .mapNotNull { it.animal }
        .firstOrNull { it.id == mayorId }
}

fun MapWithDataModel.getMayorCandidates(): List<AnimalModel> {
    return buildings
        .mapNotNull { it.animal }
        .filter {
            it.status == AnimalStatus.PET ||
                    it.status == AnimalStatus.EXPEDITION ||
                    it.status == AnimalStatus.FUGITIVE
        }
        .distinctBy { it.id }
        .sortedWith(compareBy({ it.name }, { it.id }))
}