package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CraftNotAvailableBanner(
    reason: String
) {
    Card(
        modifier = Modifier
            .padding(top = 10.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "🍰 Здесь временно нельзя готовить",
                style = MaterialTheme.typography.subtitle1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = reason,
                style = MaterialTheme.typography.body2
            )
        }
    }
}