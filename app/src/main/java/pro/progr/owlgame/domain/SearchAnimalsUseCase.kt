package pro.progr.owlgame.domain

import android.util.Log
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.BuildingsRepository
import javax.inject.Inject

class SearchAnimalsUseCase @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val buildingsRepository: BuildingsRepository
) {
    suspend operator fun invoke(): Animal? {
        Log.wtf("SEARCH ANIMALS USECASE", "INSIDE")

        if (buildingsRepository.countUninhabited() > 0
            && animalsRepository.countAnimalsSearching() == 0L) {
            return animalsRepository.getAnimal()
        }

        return  null
    }
}