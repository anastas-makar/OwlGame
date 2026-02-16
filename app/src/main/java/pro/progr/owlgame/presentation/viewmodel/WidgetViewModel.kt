package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.AnimalStatus
import pro.progr.owlgame.data.repository.WidgetRepository
import pro.progr.owlgame.presentation.ui.model.OwlMenuModel

class WidgetViewModel(
    val widgetRepository: WidgetRepository,
    )  : ViewModel() {

    val menuItems = mutableStateOf(ArrayList<OwlMenuModel>())

    fun updateMenuList() {
        viewModelScope.launch {
            val items = withContext(Dispatchers.IO) {
                MenuListWrapper(widgetRepository).menuItems
            }
            menuItems.value = items
        }

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

                if (animal.status != AnimalStatus.SEARCHING) {
                    widgetRepository.clearAnimalDayAndId()
                    return this
                }

                add(OwlMenuModel(
                    text = "${animal.name} ${animal.kind} ищет дом",
                    navigateTo = "animal?id=${animal.id}",
                    imageUri = widgetRepository.getUri(animal.imagePath)
                ))
            }

            return this
        }

        private fun ArrayList<OwlMenuModel>.withMaps() : ArrayList<OwlMenuModel> {
            add(OwlMenuModel(
                text = "Осмотреть владения",
                navigateTo = "owl_navigation",
                imageUri = widgetRepository.getUri(R.drawable.test1)
            ))

            return this
        }

        private fun ArrayList<OwlMenuModel>.withPouch() : ArrayList<OwlMenuModel> {
            if (widgetRepository.isPouchAvailable()) {
                add(OwlMenuModel(
                    text = "Открыть мешочек",
                    navigateTo = "owl_navigation/pouch",
                    imageUri = widgetRepository.getUri(R.drawable.pouch)
                ))
            }

            return this
        }
    }
}