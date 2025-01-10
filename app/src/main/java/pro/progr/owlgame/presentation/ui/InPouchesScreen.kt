package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerPouchViewModel

@Composable
fun InPouchesScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    pouchId: String,
    inPouchViewModel: InPouchViewModel = DaggerPouchViewModel()
) {

    inPouchViewModel.loadInPouch(pouchId)

    inPouchViewModel.inPouch.value?.let { inPouch ->

        Scaffold(
            topBar = {
                Box(modifier = Modifier.statusBarsPadding()) {
                    InPouchBar(backToMain, inPouch)
                }
            },
            content = { innerPadding ->

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {

                    for (map in inPouch.maps) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            AsyncImage(
                                model = Box(modifier = Modifier.fillMaxWidth()) {
                                    AsyncImage(
                                        model = map.imageUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp)
                                    )
                                },
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            )


                        }
                    }

                }
            }
        )
    }

}