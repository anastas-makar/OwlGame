package pro.progr.owlgame.presentation.viewmodel.dagger

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.presentation.viewmodel.WidgetViewModel

class WidgetViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WidgetViewModel(application) as T
    }
}