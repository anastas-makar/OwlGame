package pro.progr.owlgame.presentation.ui.model.merchant

sealed class MerchantItemKey {
    data class Building(val id: String) : MerchantItemKey()
    data class Map(val id: String) : MerchantItemKey()
    data class GardenItem(val id: String) : MerchantItemKey()
    data class Plant(val id: String) : MerchantItemKey()
    data class Furniture(val id: String) : MerchantItemKey()
    data class Recipe(val id: String) : MerchantItemKey()
    data class Location(val id: String) : MerchantItemKey()
}