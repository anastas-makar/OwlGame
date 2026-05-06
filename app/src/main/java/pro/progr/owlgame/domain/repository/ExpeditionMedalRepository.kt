package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.ExpeditionMedalModel

interface ExpeditionMedalRepository {
    fun getAnimalMedals(animalId: String): Flow<List<ExpeditionMedalModel>>
}