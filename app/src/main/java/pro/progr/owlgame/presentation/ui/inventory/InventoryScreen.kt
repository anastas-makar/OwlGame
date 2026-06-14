package pro.progr.owlgame.presentation.ui.inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.viewmodel.InventoryViewModel

@Composable
fun InventoryScreen(
    inventoryViewModel: InventoryViewModel,
    backToMain : () -> Unit
) {
    val uiState by inventoryViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(R.string.inventory_title))
            },
            navigationIcon = {
                IconButton(onClick = { backToMain() }) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Назад")
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )

        InventoryContent(
            categories = uiState.categories
        )
    }
}