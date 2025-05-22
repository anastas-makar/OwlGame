package pro.progr.owlgame.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.OwlMenuModel

class WidgetViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("animal_search_prefs", Context.MODE_PRIVATE)

    val menuList = listOf(
        OwlMenuModel(
            text = "Карты",
            navigateTo = "owl_navigation",
            imageResource = R.drawable.test1
        ),
        OwlMenuModel(
            text = "Открыть мешочек",
            navigateTo = "owl_navigation/pouch",
            imageResource = R.drawable.pouch
        )
    )
}