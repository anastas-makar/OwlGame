package pro.progr.owlgame.domain

import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.BuildingsRepository
import javax.inject.Inject

class SearchAnimalsUseCase @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    private val buildingsRepository: BuildingsRepository
) {
    operator fun invoke() {

    }
}