package pro.progr.owlgame.presentation.ui.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CircleColor(backgroundColor: Color) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .shadow(elevation = 5.dp)
            .size(30.dp)
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(16.dp),
            tint = MaterialTheme.colors.surface
        )
    }
}