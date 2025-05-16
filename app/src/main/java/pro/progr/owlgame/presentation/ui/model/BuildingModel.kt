package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.Animal

data class BuildingModel (
    val id : String,
    val name : String,
    val imageResource : String,
    val animal : Animal? = null
)