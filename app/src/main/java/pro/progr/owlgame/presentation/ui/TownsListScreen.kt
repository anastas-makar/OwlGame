package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun TownsListScreen(backToMain : () -> Unit,
                    navController : NavHostController) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                TownsListBar(backToMain)
            }
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {

                Text("List of towns")

                TextButton(onClick = { navController.navigate("town/1") }) {
                    Text(text = "Кубинка 1")
                }

                TextButton(onClick = { navController.navigate("town/2") }) {
                    Text(text = "Кубинка 2")
                }
            }


        }
    )
}