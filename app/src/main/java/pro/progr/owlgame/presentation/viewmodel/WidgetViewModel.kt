package pro.progr.owlgame.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class WidgetViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("animal_search_prefs", Context.MODE_PRIVATE)

}