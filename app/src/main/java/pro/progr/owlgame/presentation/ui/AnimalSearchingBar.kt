package pro.progr.owlgame.presentation.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.data.db.Animal

@Composable
fun AnimalSearchingBar(backToMain : () -> Unit,
                       animalState: State<Animal?>
) {

    TopAppBar(
        title = {
            Text(text = if (animalState.value != null)
                "${animalState.value?.name} ищет дом"
                else "")
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