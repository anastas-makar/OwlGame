package pro.progr.owlgame.domain.usecase

import android.util.Log
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.BuildingsRepository
import javax.inject.Inject

class SearchingAnimalUseCase @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val buildingsRepository: BuildingsRepository
) {
    suspend operator fun invoke(): AnimalModel? {
        Log.wtf("SEARCH ANIMALS USECASE", "INSIDE")

        if (buildingsRepository.countUninhabited() > 0) {
            animalsRepository.findSearchingAnimalInDataBase()?.let {
                return it
            }

            animalsRepository.getApiAnimal()?.let {
                return animalsRepository.saveAnimal(it)
            }
        }

        return  null
    }
}