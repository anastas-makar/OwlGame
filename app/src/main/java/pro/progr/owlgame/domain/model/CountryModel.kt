package pro.progr.owlgame.domain.model

data class CountryModel(
    val id: String,
    val name: String,
    val animalRulerId: String? = null
)