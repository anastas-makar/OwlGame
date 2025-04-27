package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import javax.inject.Inject

class AnimalViewModel @Inject constructor(
    animalsRepository: AnimalsRepository,
    mapsRepository: MapsRepository,
    animalId: String
) : ViewModel() {
    val animal = animalsRepository.getAnimalById(animalId)

    val mapsWithUninhabitedBuildings = mapsRepository.getMapsWithUninhabitedBuildings()
}