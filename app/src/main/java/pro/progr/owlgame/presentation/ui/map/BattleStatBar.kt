package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BattleStatBar(
    label: String,
    current: Int,
    max: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "$label: $current / $max")
        LinearProgressIndicator(
            progress = progress(current, max),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
    }
}

private fun progress(current: Int, max: Int): Float {
    if (max <= 0) return 0f
    return (current.toFloat() / max.toFloat()).coerceIn(0f, 1f)
}