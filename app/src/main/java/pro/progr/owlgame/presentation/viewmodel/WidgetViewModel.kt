package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pro.progr.owlgame.R
import pro.progr.owlgame.data.repository.WidgetRepository
import pro.progr.owlgame.presentation.ui.model.OwlMenuModel

class WidgetViewModel(
    widgetRepository: WidgetRepository,
    )  : ViewModel() {

    private val menuListWrapper = MenuListWrapper(widgetRepository)
    val menuItems = mutableStateOf(ArrayList<OwlMenuModel>())

    fun updateMenuList() {
        menuItems.value = menuListWrapper.menuItems
    }

    class MenuListWrapper(private val widgetRepository: WidgetRepository) {
        val menuItems : ArrayList<OwlMenuModel>
            get() {
                return ArrayList<OwlMenuModel>()
                    .withAnimalSearching()
                    .withPouch()
                    .withMaps()
            }

        private fun ArrayList<OwlMenuModel>.withAnimalSearching() : ArrayList<OwlMenuModel> {
            val animal = widgetRepository.getAnimal()
            if (animal !=null) {

                add(OwlMenuModel(
                    text = "${animal.name} ищет дом",
                    navigateTo = "owl_navigation/animal_searching/${animal.id}",
                    imagePath = animal.imagePath
                ))
            }

            return this
        }

        private fun ArrayList<OwlMenuModel>.withMaps() : ArrayList<OwlMenuModel> {
            add(OwlMenuModel(
                text = "Карты",
                navigateTo = "owl_navigation",
                imagePath = R.drawable.test1
            ))

            return this
        }

        private fun ArrayList<OwlMenuModel>.withPouch() : ArrayList<OwlMenuModel> {
            if (widgetRepository.isPouchAvailable()) {
                add(OwlMenuModel(
                    text = "Открыть мешочек",
                    navigateTo = "owl_navigation/pouch",
                    imagePath = R.drawable.pouch
                ))
            }

            return this
        }
    }
}