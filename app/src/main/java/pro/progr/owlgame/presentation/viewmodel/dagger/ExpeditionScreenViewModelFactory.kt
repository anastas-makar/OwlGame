package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.presentation.viewmodel.ExpeditionScreenViewModel
import javax.inject.Inject

class ExpeditionScreenViewModelFactory @Inject constructor(
    private val expeditionsRepository: ExpeditionsRepository,
    private val animalsRepository: AnimalsRepository
) : ViewModelProvider.Factory {
    var mapId : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpeditionScreenViewModel::class.java)) {
            return ExpeditionScreenViewModel(
                expeditionsRepository,
                animalsRepository,
                mapId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}