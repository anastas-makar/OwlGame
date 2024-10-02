package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.presentation.viewmodel.TownsViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val townRepository: TownRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TownsViewModel::class.java)) {
            return TownsViewModel(townRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}