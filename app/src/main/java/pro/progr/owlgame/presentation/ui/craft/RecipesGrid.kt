package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.RecipeModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipesGrid(
    recipes: List<RecipeModel>,
    onClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(recipes.size, key = { recipes[it].recipeId }) { i ->
            val recipe = recipes[i]
            RecipeCard(recipe = recipe, onClick = { onClick(recipes[i].recipeId) })
        }
    }
}