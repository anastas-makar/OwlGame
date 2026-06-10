package pro.progr.owlgame.presentation.ui.pouch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.presentation.ui.model.LootHintUi

@Composable
fun HintCard(hint: LootHintUi) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 3.dp,
        shape = RoundedCornerShape(18.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = hint.title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(Modifier.height(8.dp))

            hint.paragraphs.forEachIndexed { index, paragraph ->
                if (index > 0) {
                    Spacer(Modifier.height(8.dp))
                }

                Text(
                    text = paragraph,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.82f)
                )
            }
        }
    }
}