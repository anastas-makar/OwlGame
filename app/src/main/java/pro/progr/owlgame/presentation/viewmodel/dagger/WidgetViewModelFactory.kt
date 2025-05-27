package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.WidgetRepository
import pro.progr.owlgame.presentation.viewmodel.WidgetViewModel
import javax.inject.Inject

class WidgetViewModelFactory @Inject constructor(private val widgetRepository: WidgetRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WidgetViewModel(widgetRepository) as T
    }
}