package pro.progr.owlgame.presentation.ui.model.mapitem

sealed interface OccupiedMapItem {
    val id: String
    val x: Float
    val y: Float
}