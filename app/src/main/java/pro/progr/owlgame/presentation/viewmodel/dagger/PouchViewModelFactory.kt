package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.domain.SaveBuildingsUseCase
import pro.progr.owlgame.domain.SaveMapsUseCase
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel
import pro.progr.owlgame.presentation.viewmodel.PouchesViewModel
import javax.inject.Inject

class PouchViewModelFactory @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val saveMapsUseCase: SaveMapsUseCase,
    private val saveBuildingsUseCase: SaveBuildingsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PouchesViewModel::class.java)) {
            return PouchesViewModel(pouchesRepository) as T
        } else if (modelClass.isAssignableFrom(InPouchViewModel::class.java)) {
            return InPouchViewModel(pouchesRepository, saveMapsUseCase, saveBuildingsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}