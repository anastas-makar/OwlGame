package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.presentation.viewmodel.WidgetViewModel
import javax.inject.Inject

class WidgetViewModelFactory @Inject constructor(private val preferences: OwlPreferences)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WidgetViewModel(preferences) as T
    }
}