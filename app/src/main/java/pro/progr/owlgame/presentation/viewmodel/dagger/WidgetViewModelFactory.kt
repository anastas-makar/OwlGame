package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.WidgetRepository
import pro.progr.owlgame.presentation.resources.StringProvider
import pro.progr.owlgame.presentation.viewmodel.WidgetViewModel
import javax.inject.Inject

class WidgetViewModelFactory @Inject constructor(
    private val widgetRepository: WidgetRepository,
    private val stringProvider: StringProvider)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WidgetViewModel(widgetRepository,
            stringProvider) as T
    }
}