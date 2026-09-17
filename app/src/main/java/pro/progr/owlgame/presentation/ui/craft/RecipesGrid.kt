package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.RecipeModel
import pro.progr.owlgame.domain.model.SupplyModel
import pro.progr.owlgame.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CraftGrid(
    recipes: List<RecipeModel>,
    supplies: List<SupplyModel>,
    onRecipeClick: (String) -> Unit,
    onSupplyClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Text(
                text = stringResource(R.string.recipes_label),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
        }

        items(
            count = recipes.size,
            key = { recipes[it].recipeId }
        ) { i ->
            val recipe = recipes[i]

            RecipeCard(
                recipe = recipe,
                onClick = { onRecipeClick(recipe.recipeId) }
            )
        }

        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Spacer(Modifier.height(8.dp))
        }

        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Text(
                text = stringResource(R.string.available_supplies_label),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
        }

        if (supplies.isEmpty()) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text(
                    text = stringResource(R.string.nothing_available_yet),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        } else {
            items(
                count = supplies.size,
                key = { supplies[it].id }
            ) { i ->
                val supply = supplies[i]

                SupplyCard(
                    supply = supply,
                    onClick = { onSupplyClick(supply.id) }
                )
            }
        }
    }
}
