package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.navigation.NavHostController
import pro.progr.owlgame.presentation.viewmodel.TownsViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerViewModel

@Composable
fun TownsListScreen(backToMain : () -> Unit,
                    navController : NavHostController,
                    townsViewModel: TownsViewModel = DaggerViewModel()) {
    val townsList = townsViewModel.townsList.collectAsState(initial = emptyList())

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

                LazyColumn {
                    itemsIndexed(townsList.value) { _, town ->
                        TextButton(onClick = { navController.navigate("town/${town.id}") }) {
                            Text(text = town.name)
                        }
                    }

                }
            }


        }
    )
}