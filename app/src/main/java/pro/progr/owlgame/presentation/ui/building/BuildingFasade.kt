package pro.progr.owlgame.presentation.ui.building

import androidx.compose.runtime.Composable
import pro.progr.owlgame.data.db.Building

@Composable
fun BuildingFasade(building: Building) {
    LargeImage(imageUrl = building.imageUrl)
}