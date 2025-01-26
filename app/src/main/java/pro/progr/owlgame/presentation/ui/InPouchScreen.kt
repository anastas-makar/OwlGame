package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerPouchViewModel

@Composable
fun InPouchScreen(
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
                    if (inPouch.diamonds != null) {
                        Text("+${inPouch.diamonds.amount} "
                            + LocalContext.current.resources
                                .getQuantityString(R.plurals.word_diamond,
                                    inPouch.diamonds.amount),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp))
                    }

                    for (map in inPouch.maps) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "+ ${map.name}", fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp))
                            AsyncImage(
                                model = Box(modifier = Modifier.fillMaxWidth()) {
                                    AsyncImage(
                                        model = map.imageUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
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

                    for (building in inPouch.buildings) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            AsyncImage(
                                model = Box(modifier = Modifier.fillMaxWidth()) {
                                    AsyncImage(
                                        model = building.imageUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
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