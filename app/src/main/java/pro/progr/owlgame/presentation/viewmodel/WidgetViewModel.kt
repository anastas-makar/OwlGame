package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.repository.WidgetRepository
import pro.progr.owlgame.presentation.resources.StringProvider
import pro.progr.owlgame.presentation.ui.model.OwlMenuModel
import pro.progr.owlgame.presentation.ui.model.WidgetMenuUiState

class WidgetViewModel(
    private val widgetRepository: WidgetRepository,
    private val stringProvider: StringProvider
) : ViewModel() {

    val uiState = mutableStateOf(WidgetMenuUiState())
    val isLoading = derivedStateOf { uiState.value.isLoading }
    val menuItems = derivedStateOf { uiState.value.menuItems }
    private val refresh = MutableStateFlow(0L)

    init {
        viewModelScope.launch {
            combine(
                widgetRepository.observeInitialRestoreCompleted(),
                widgetRepository.observeSearchingAnimal(),
                refresh
            ) { restoreCompleted, searchingAnimal, _ ->
                restoreCompleted to searchingAnimal
            }.collect { (restoreCompleted, searchingAnimal) ->
                if (!restoreCompleted) {
                    uiState.value = WidgetMenuUiState(isLoading = true)
                    return@collect
                }

                val items = withContext(Dispatchers.IO) {
                    MenuListWrapper(
                        widgetRepository = widgetRepository,
                        stringProvider = stringProvider,
                        searchingAnimal = searchingAnimal
                    ).menuItems
                }

                uiState.value = WidgetMenuUiState(
                    isLoading = false,
                    menuItems = items
                )
            }
        }
    }

    fun updateMenuList() {
        refresh.update { it + 1L }
    }

    class MenuListWrapper(
        private val widgetRepository: WidgetRepository,
        private val stringProvider: StringProvider,
        private val searchingAnimal: AnimalModel?
    ) {
        val menuItems: ArrayList<OwlMenuModel>
            get() {
                return ArrayList<OwlMenuModel>()
                    .withAnimalSearching(searchingAnimal)
                    .withMerchant()
                    .withPouch()
                    .withMaps()
                    .withInventory()
            }

        private fun ArrayList<OwlMenuModel>.withAnimalSearching(
            animal: AnimalModel?
        ): ArrayList<OwlMenuModel> {
            if (animal != null) {
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

        private fun ArrayList<OwlMenuModel>.withMerchant(): ArrayList<OwlMenuModel> {
            if (widgetRepository.isMerchantAvailable()) {
                add(
                    OwlMenuModel(
                        text = stringProvider.getString(R.string.wandering_merchant_available),
                        navigateTo = "owl_navigation/merchant",
                        imageUri = widgetRepository.getUri(R.drawable.merchant)
                    )
                )

            }

            return this
        }
    }
}
