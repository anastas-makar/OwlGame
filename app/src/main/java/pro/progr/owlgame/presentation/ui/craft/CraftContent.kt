package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pro.progr.owlgame.presentation.viewmodel.CraftViewModel

@Composable
fun CraftContent(vm: CraftViewModel) {
    val recipes by vm.recipes.collectAsState()
    val availableSupplies by vm.availableSupplies.collectAsState()

    val selectedRecipe by vm.selectedRecipe.collectAsState()
    val selectedSupply by vm.selectedSupply.collectAsState()

    CraftGrid(
        recipes = recipes,
        supplies = availableSupplies,
        onRecipeClick = vm::onRecipeClick,
        onSupplyClick = vm::onSupplyClick
    )

    selectedRecipe?.let { recipe ->
        RecipeDialog(
            recipe = recipe,
            onDismiss = vm::dismissRecipeDialog,
            onCraft = vm::craftSelected
        )
    }

    selectedSupply?.let { supply ->
        SupplyDialog(
            supply = supply,
            onDismiss = vm::dismissSupplyDialog
        )
    }
}

