package pro.progr.owlgame.presentation.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.presentation.viewmodel.PouchesViewModel

@Composable
fun PouchesBar(backToMain : () -> Unit,
               pouchesViewModel: PouchesViewModel) {
    TopAppBar(
        title = {
            Text(text = if (pouchesViewModel.isPouchSelected.value)
                    "Вы открыли мешочек"
                else "Выберите мешочек")
        },
        navigationIcon = {
            IconButton(onClick = { backToMain() }) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Назад")
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}