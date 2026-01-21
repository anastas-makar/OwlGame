package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import javax.inject.Inject

class FabViewModelFactory @Inject constructor(
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FabViewModel::class.java)) {
            return FabViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}