package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.repository.WidgetRepository
import pro.progr.owlgame.presentation.resources.StringProvider
import pro.progr.owlgame.presentation.ui.model.OwlMenuModel

class WidgetViewModel(
    private val widgetRepository: WidgetRepository,
    private val stringProvider: StringProvider
) : ViewModel() {

    val menuItems = mutableStateOf(emptyList<OwlMenuModel>())

    fun updateMenuList() {
        viewModelScope.launch {
            val items = withContext(Dispatchers.IO) {
                MenuListWrapper(
                    widgetRepository = widgetRepository,
                    stringProvider = stringProvider
                ).menuItems
            }

            menuItems.value = items
        }
    }

    class MenuListWrapper(
        private val widgetRepository: WidgetRepository,
        private val stringProvider: StringProvider
    ) {
        val menuItems: ArrayList<OwlMenuModel>
            get() {
                return ArrayList<OwlMenuModel>()
                    .withAnimalSearching()
                    .withPouch()
                    .withMaps()
                    .withInventory()
            }

        private fun ArrayList<OwlMenuModel>.withAnimalSearching(): ArrayList<OwlMenuModel> {
            val animal = widgetRepository.getAnimal()

            if (animal != null) {
                if (animal.status != AnimalStatus.SEARCHING) {
                    widgetRepository.clearAnimalDayAndId()
                    return this
                }

                add(
                    OwlMenuModel(
                        text = stringProvider.getString(
                            R.string.widget_menu_animal_searching_home,
                            animal.initialDisplayName
                        ),
                        navigateTo = "animal?id=${animal.id}",
                        imageUri = widgetRepository.getUri(animal.imagePath)
                    )
                )
            }

            return this
        }

        private fun ArrayList<OwlMenuModel>.withMaps(): ArrayList<OwlMenuModel> {
            val randomMap = widgetRepository.getRandomMap()

            if (randomMap != null) {
                add(
                    OwlMenuModel(
                        text = stringProvider.getString(R.string.widget_menu_visit_lands),
                        navigateTo = "owl_navigation",
                        imageUri = widgetRepository.getUri(randomMap.imageUrl)
                    )
                )
            }

            return this
        }

        private fun ArrayList<OwlMenuModel>.withPouch(): ArrayList<OwlMenuModel> {
            if (widgetRepository.isPouchAvailable()) {
                add(
                    OwlMenuModel(
                        text = stringProvider.getString(R.string.widget_menu_open_pouch),
                        navigateTo = "owl_navigation/pouch",
                        imageUri = widgetRepository.getUri(R.drawable.pouch)
                    )
                )
            }

            return this
        }

        private fun ArrayList<OwlMenuModel>.withInventory(): ArrayList<OwlMenuModel> {
            add(
                OwlMenuModel(
                    text = stringProvider.getString(R.string.widget_menu_open_inventory),
                    navigateTo = "owl_navigation/inventory",
                    imageUri = widgetRepository.getUri(R.drawable.inventory)
                )
            )

            return this
        }
    }
}