package pro.progr.owlgame.presentation.ui.fab

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class FabAction(
    val text: String,
    val color: Color,
    val onClick: () -> Unit
)