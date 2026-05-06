package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import pro.progr.owlgame.domain.model.ExpeditionMedalModel
import pro.progr.owlgame.domain.repository.ExpeditionMedalRepository
import javax.inject.Inject

class BuildingFacadeViewModel @Inject constructor(
    private val medalsRepository: ExpeditionMedalRepository,
    private val animalId: String?
) : ViewModel() {

    val medals: StateFlow<List<ExpeditionMedalModel>> =
        if (animalId == null) {
            flowOf(emptyList())
        } else {
            medalsRepository.getAnimalMedals(animalId)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}