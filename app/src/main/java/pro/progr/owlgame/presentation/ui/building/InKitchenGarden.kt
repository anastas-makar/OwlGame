package pro.progr.owlgame.presentation.ui.building

import androidx.compose.runtime.Composable
import pro.progr.owlgame.data.db.Garden

@Composable
fun InKitchenGarden(garden: Garden) {
    LargeImage(imageUrl = garden.imageUrl)
}