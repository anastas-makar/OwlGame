package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.domain.model.IngredientModel
import pro.progr.owlgame.domain.model.RecipeModel

@Composable
fun RecipeDialog(
    recipe: RecipeModel,
    onDismiss: () -> Unit,
    onCraft: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = recipe.resultImageUrl,
                    contentDescription = recipe.resultName,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text(recipe.resultName, maxLines = 2)
            }
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Text(recipe.description)
                Spacer(Modifier.height(12.dp))

                Text("Ингредиенты", style = MaterialTheme.typography.subtitle2)
                Spacer(Modifier.height(8.dp))

                recipe.ingredients.forEach { ing ->
                    IngredientRow(ing)
                }

                val missing = recipe.ingredients.filter { !it.enough }
                if (missing.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Не хватает: " + missing.joinToString { it.name },
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onCraft,
                enabled = recipe.craftable
            ) { Text("Приготовить") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Закрыть") }
        }
    )
}

@Composable
private fun IngredientRow(ing: IngredientModel) {
    val color = if (ing.enough) MaterialTheme.colors.onSurface else MaterialTheme.colors.error

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ing.imageUrl,
            contentDescription = ing.name,
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.width(10.dp))

        Text(
            text = ing.name,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )

        // "нужно (есть)"
        Text(
            text = "${ing.required} (${ing.have})",
            color = color
        )
    }
}