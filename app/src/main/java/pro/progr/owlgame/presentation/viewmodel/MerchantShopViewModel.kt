package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pro.progr.diamondapi.GetDiamondsCountInterface
import pro.progr.owlgame.domain.model.MerchantShopModel
import pro.progr.owlgame.domain.model.PouchItemsModel
import pro.progr.owlgame.domain.usecase.BuyMerchantItemUseCase
import pro.progr.owlgame.domain.usecase.GetMerchantShopUseCase
import pro.progr.owlgame.presentation.mapper.toMerchantSections
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantItemKey
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopUiState
import javax.inject.Inject

class MerchantShopViewModel @Inject constructor(
    private val getMerchantShopUseCase: GetMerchantShopUseCase,
    private val buyMerchantItemUseCase: BuyMerchantItemUseCase,
    private val diamondsProvider: GetDiamondsCountInterface
) : ViewModel() {

    private val _ui = MutableStateFlow(
        MerchantShopUiState(isLoading = true)
    )
    val ui: StateFlow<MerchantShopUiState> = _ui.asStateFlow()

    private var currentShop: MerchantShopModel? = null
    private var purchaseCount: Int = 0

    init {
        observeDiamonds()
        loadShop()
    }

    fun loadShop() {
        viewModelScope.launch {
            _ui.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            runCatching {
                getMerchantShopUseCase()
            }.onSuccess { shop ->
                currentShop = shop
                purchaseCount = 0
                shop?.let {
                    updateUiFromShop(shop, isLoading = false)
                }
            }.onFailure { error ->
                _ui.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Unable to load merchant shop"
                    )
                }
            }
        }
    }

    fun buyItem(key: MerchantItemKey) {
        val shop = currentShop ?: return
        val price = shop.pricePolicy.priceForPurchaseCount(purchaseCount)

        if (_ui.value.diamonds < price) {
            return
        }

        val itemToBuy = shop.items.onlyItem(key) ?: return

        viewModelScope.launch {
            _ui.update {
                it.copy(
                    isBuying = true,
                    errorMessage = null
                )
            }

            runCatching {
                buyMerchantItemUseCase(
                    item = itemToBuy,
                    price = price
                )
            }.onSuccess {
                val updatedItems = shop.items.removeItem(key)
                val updatedShop = shop.copy(items = updatedItems)

                currentShop = updatedShop
                purchaseCount += 1

                updateUiFromShop(
                    shop = updatedShop,
                    isLoading = false,
                    isBuying = false
                )
            }.onFailure { error ->
                _ui.update {
                    it.copy(
                        isBuying = false,
                        errorMessage = error.message ?: "Unable to buy item"
                    )
                }
            }
        }
    }

    private fun observeDiamonds() {
        viewModelScope.launch {
            diamondsProvider.getDiamondsCount().collect { diamonds ->
                _ui.update {
                    it.copy(diamonds = diamonds)
                }
            }
        }
    }

    private fun updateUiFromShop(
        shop: MerchantShopModel,
        isLoading: Boolean,
        isBuying: Boolean = false
    ) {
        _ui.update {
            it.copy(
                title = shop.title,
                description = shop.description,
                currentPrice = shop.pricePolicy.priceForPurchaseCount(purchaseCount),
                sections = shop.items.toMerchantSections(),
                isLoading = isLoading,
                isBuying = isBuying,
                errorMessage = null
            )
        }
    }
}

fun PouchItemsModel.onlyItem(key: MerchantItemKey): PouchItemsModel? {
    return when (key) {
        is MerchantItemKey.Building -> {
            val item = buildings.firstOrNull { it.id == key.id } ?: return null
            emptyPouchItems().copy(buildings = listOf(item))
        }

        is MerchantItemKey.Map -> {
            val item = maps.firstOrNull { it.id == key.id } ?: return null
            emptyPouchItems().copy(maps = listOf(item))
        }

        is MerchantItemKey.GardenItem -> {
            val item = gardenItems.firstOrNull { it.id == key.id } ?: return null
            emptyPouchItems().copy(gardenItems = listOf(item))
        }

        is MerchantItemKey.Plant -> {
            val item = plants.firstOrNull { it.id == key.id } ?: return null
            emptyPouchItems().copy(plants = listOf(item))
        }

        is MerchantItemKey.Furniture -> {
            val item = furniture.firstOrNull { it.id == key.id } ?: return null
            emptyPouchItems().copy(furniture = listOf(item))
        }

        is MerchantItemKey.Recipe -> {
            val item = recipes.firstOrNull { it.recipeId == key.id } ?: return null
            emptyPouchItems().copy(recipes = listOf(item))
        }

        is MerchantItemKey.Location -> {
            val item = locations.firstOrNull { it.id == key.id } ?: return null
            emptyPouchItems().copy(locations = listOf(item))
        }
    }
}

fun PouchItemsModel.removeItem(key: MerchantItemKey): PouchItemsModel {
    return when (key) {
        is MerchantItemKey.Building -> copy(
            buildings = buildings.filterNot { it.id == key.id }
        )

        is MerchantItemKey.Map -> copy(
            maps = maps.filterNot { it.id == key.id }
        )

        is MerchantItemKey.GardenItem -> copy(
            gardenItems = gardenItems.filterNot { it.id == key.id }
        )

        is MerchantItemKey.Plant -> copy(
            plants = plants.filterNot { it.id == key.id }
        )

        is MerchantItemKey.Furniture -> copy(
            furniture = furniture.filterNot { it.id == key.id }
        )

        is MerchantItemKey.Recipe -> copy(
            recipes = recipes.filterNot { it.recipeId == key.id }
        )

        is MerchantItemKey.Location -> copy(
            locations = locations.filterNot { it.id == key.id }
        )
    }

}

fun emptyPouchItems(): PouchItemsModel {
    return PouchItemsModel(
        buildings = emptyList(),
        maps = emptyList(),
        diamonds = null,
        gardenItems = emptyList(),
        plants = emptyList(),
        furniture = emptyList(),
        recipes = emptyList(),
        locations = emptyList()
    )
}