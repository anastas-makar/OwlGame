package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import pro.progr.owlgame.presentation.viewmodel.CraftViewModel

@Composable
fun CraftScreen(
    navController: NavHostController,
    vm: CraftViewModel
) {


        Scaffold(
            topBar = {
                Box(modifier = Modifier.statusBarsPadding()) {
                    CraftBar(navController)
                }
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()) {
                    CraftContent(vm)
                }
            }
        )
}
