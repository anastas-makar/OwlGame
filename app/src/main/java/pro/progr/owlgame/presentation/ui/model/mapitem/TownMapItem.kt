package pro.progr.owlgame.presentation.ui.model.mapitem

sealed interface TownMapItem {
    val id: String
    val x: Float
    val y: Float
}