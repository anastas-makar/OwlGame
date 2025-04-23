package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.repository.AnimalsRepository
import javax.inject.Inject

class AnimalViewModel @Inject constructor(
    val animalsRepository: AnimalsRepository,
    val animalId: String
) : ViewModel() {
    val animal = animalsRepository.getAnimalById(animalId)

}