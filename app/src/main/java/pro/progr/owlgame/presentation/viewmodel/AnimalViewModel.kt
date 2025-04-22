package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalStatus
import pro.progr.owlgame.data.repository.AnimalsRepository
import javax.inject.Inject

class AnimalViewModel @Inject constructor(
    val animalsRepository: AnimalsRepository,
    val animalId: String
) : ViewModel() {
    private lateinit var animal : Animal

    suspend fun getAnimal() : Animal {
        if (!this::animal.isInitialized) {
            val nAnimal = animalsRepository.getAnimalById(animalId)

            if (nAnimal == null) {
                animal = Animal(
                    animalId,
                    "Неизвестное животное",
                    "", AnimalStatus.GONE)
            } else {
                animal = nAnimal
            }
        }

        return animal
    }

}