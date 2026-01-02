package pro.progr.owlgame.presentation.ui.building

import androidx.compose.runtime.Composable
import pro.progr.owlgame.data.db.Garden

@Composable
fun InGarden(garden: Garden) {
    LargeImage(imageUrl = garden.imageUrl)
}