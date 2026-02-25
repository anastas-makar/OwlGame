package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import pro.progr.owlgame.presentation.viewmodel.CraftViewModel

@Composable
fun CraftScreen(vm: CraftViewModel) {
    val recipes by vm.recipes.collectAsState()
    val selected by vm.selectedRecipe.collectAsState()

    // если хочешь тосты/снэкбар — добавишь scaffold + LaunchedEffect(vm.toast)
    Box(Modifier.fillMaxSize()) {
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
}

