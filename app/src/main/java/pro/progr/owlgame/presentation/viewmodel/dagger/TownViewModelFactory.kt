package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.presentation.viewmodel.TownViewModel
import javax.inject.Inject

class TownViewModelFactory @Inject constructor(
    private val townRepository: TownRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TownViewModel::class.java)) {
            return TownViewModel(townRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}