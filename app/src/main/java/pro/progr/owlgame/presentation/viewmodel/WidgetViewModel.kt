package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.R
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.presentation.ui.model.OwlMenuModel
import java.time.LocalDate

class WidgetViewModel(
    preferences: OwlPreferences,
    )  : ViewModel() {

    val menuListWrapper = MenuListWrapper(preferences)

    fun getMenuList() : ArrayList<OwlMenuModel> {
        return menuListWrapper.menuItems
    }

    class MenuListWrapper(private val preferences: OwlPreferences) {
        val menuItems : ArrayList<OwlMenuModel>
            get() {
                return ArrayList<OwlMenuModel>()
                    .withAnimalSearching()
                    .withPouch()
                    .withMaps()
            }

        private fun ArrayList<OwlMenuModel>.withAnimalSearching() : ArrayList<OwlMenuModel> {
            val animalId = preferences.getAnimalId()
            if (animalId !=null) {

                add(OwlMenuModel(
                    text = "Животное ищет дом",
                    navigateTo = "owl_navigation/animal_searching/$animalId",
                    imageResource = R.drawable.test1
                ))
            }

            return this
        }

        private fun ArrayList<OwlMenuModel>.withMaps() : ArrayList<OwlMenuModel> {
            add(OwlMenuModel(
                text = "Карты",
                navigateTo = "owl_navigation",
                imageResource = R.drawable.test1
            ))

            return this
        }

        private fun ArrayList<OwlMenuModel>.withPouch() : ArrayList<OwlMenuModel> {
            val lastPouchDay = preferences.getLastPouchOpenDay()
            if (lastPouchDay < LocalDate.now().toEpochDay()) {
                add(OwlMenuModel(
                    text = "Открыть мешочек",
                    navigateTo = "owl_navigation/pouch",
                    imageResource = R.drawable.pouch
                ))
            }

            return this
        }
    }
}