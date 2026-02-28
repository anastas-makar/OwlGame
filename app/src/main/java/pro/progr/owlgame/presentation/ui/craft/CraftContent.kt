package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pro.progr.owlgame.presentation.viewmodel.CraftViewModel

@Composable
fun CraftContent(vm: CraftViewModel) {
    val recipes by vm.recipes.collectAsState()
    val selected by vm.selectedRecipe.collectAsState()

    RecipesGrid(
        recipes = recipes,
        onClick = vm::onRecipeClick
    )

    if (selected != null) {
        RecipeDialog(
            recipe = selected!!,
            onDismiss = vm::dismissDialog,
            onCraft = vm::craftSelected
        )
    }
}

