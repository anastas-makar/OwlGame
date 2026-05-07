package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.PouchesRepository
import pro.progr.owlgame.domain.usecase.SavePouchUseCase
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel
import pro.progr.owlgame.presentation.viewmodel.PouchesViewModel
import javax.inject.Inject

class PouchViewModelFactory @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val savePouchUseCase: SavePouchUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PouchesViewModel::class.java)) {
            return PouchesViewModel(pouchesRepository) as T
        } else if (modelClass.isAssignableFrom(InPouchViewModel::class.java)) {
            return InPouchViewModel(pouchesRepository,
                savePouchUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}