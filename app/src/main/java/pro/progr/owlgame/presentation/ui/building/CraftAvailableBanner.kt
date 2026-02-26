package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.clickable
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
fun CraftAvailableBanner(
    animalKind: String,
    animalName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 10.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "🍰 Здесь можно готовить",
                style = MaterialTheme.typography.subtitle1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "В комнате есть холодильник. $animalKind $animalName может здесь готовить.",
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Открыть рецепты",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary
            )
        }
    }
}