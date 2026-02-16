package pro.progr.owlgame.domain

import android.util.Log
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.BuildingsRepository
import javax.inject.Inject

class SearchingAnimalUseCase @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val buildingsRepository: BuildingsRepository
) {
    suspend operator fun invoke(): Animal? {
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