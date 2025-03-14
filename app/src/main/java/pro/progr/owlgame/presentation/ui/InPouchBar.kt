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
import pro.progr.owlgame.data.web.inpouch.InPouch
import pro.progr.owlgame.presentation.ui.model.InPouchDescription

@Composable
fun InPouchBar(backToMain : () -> Unit,
               inPouch: InPouch) {
    TopAppBar(
        title = {
            Text(text = InPouchDescription(inPouch).compile())
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