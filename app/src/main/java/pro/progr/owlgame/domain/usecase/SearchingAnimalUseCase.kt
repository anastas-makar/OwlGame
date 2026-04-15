package pro.progr.owlgame.domain.usecase

import android.util.Log
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import javax.inject.Inject

class SearchingAnimalUseCase @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val buildingsRepository: BuildingsRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(): AnimalModel? {
        Log.wtf("SEARCH ANIMALS USECASE", "INSIDE")

        if (buildingsRepository.countUninhabited() > 0) {
            animalsRepository.findSearchingAnimalInDataBase()?.let {
                return it
            }

            animalsRepository.getApiAnimal()?.let {
                val savedAnimal = it.copy(
                    imagePath = imageRepository.saveImageLocally(it.imagePath))
                animalsRepository.saveAnimal(savedAnimal)

                return savedAnimal
            }
        }

        return  null
    }
}