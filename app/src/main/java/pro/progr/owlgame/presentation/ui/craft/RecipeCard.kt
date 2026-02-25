package pro.progr.owlgame.presentation.ui.craft

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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.domain.model.RecipeModel

@Composable
fun RecipeCard(
    recipe: RecipeModel,
    onClick: () -> Unit
) {
    val alpha = if (recipe.craftable) 1f else 0.45f

    Card(
        elevation = 4.dp,
        modifier = Modifier.Companion
            .fillMaxWidth()
            .graphicsLayer { this.alpha = alpha }
            .clickable { onClick() } // кликабельна всегда
    ) {
        Column(Modifier.Companion.padding(12.dp)) {
            // картинка результата
            AsyncImage(
                model = recipe.resultImageUrl,
                contentDescription = recipe.resultName,
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .height(110.dp)
            )

            Spacer(Modifier.Companion.height(8.dp))

            Text(
                text = recipe.resultName,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1
            )

            Spacer(Modifier.Companion.height(4.dp))

            Text(
                text = recipe.description,
                style = MaterialTheme.typography.body2,
                maxLines = 3
            )

            if (!recipe.craftable) {
                Spacer(Modifier.Companion.height(8.dp))
                Text(
                    text = "Не хватает ингредиентов",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}