package pro.progr.owlgame.presentation.ui.fab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun FloatingActionTextButton(
    action: FabAction,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = action.text,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .shadow(elevation = 1.dp)
                .background(MaterialTheme.colors.surface)
                .padding(8.dp),
            style = MaterialTheme.typography.button
        )
        Spacer(Modifier.width(16.dp))
        CircleColor(backgroundColor = action.color)
    }
}