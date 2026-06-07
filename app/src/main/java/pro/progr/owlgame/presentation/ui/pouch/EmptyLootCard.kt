package pro.progr.owlgame.presentation.ui.pouch

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyLootCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 3.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = "В подземельях ничего не нашлось.",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.body1
        )
    }
}