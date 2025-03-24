package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    inPouchViewModel.inPouch.value?.let { inPouch ->
        Scaffold(
            topBar = {
                Box(modifier = Modifier.statusBarsPadding()) {
                    InPouchBar(backToMain, inPouch)
                }
            },
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Если есть бриллианты
                inPouch.diamonds?.let { diamondsData ->
                    item {
                        Text(
                            text = "+${diamondsData.amount} " + LocalContext.current.resources
                                .getQuantityString(
                                    R.plurals.word_diamond,
                                    diamondsData.amount
                                ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                // Список карт
                itemsIndexed(inPouch.maps) { _, map ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "+ ${map.name}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate("map/${map.id}")
                                }
                        ) {
                            AsyncImage(
                                model = map.imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                // Список зданий
                itemsIndexed(inPouch.buildings) { _, building ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Box {
                            Text(
                                text = building.name,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterStart)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(2.dp)
                            ) {
                                AsyncImage(
                                    model = building.imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .widthIn(100.dp, 300.dp)
                                        .align(Alignment.CenterEnd)
                                )
                            }

                        }
                    }
                }
            }
        }
    } ?: run {
        inPouchViewModel.loadInPouch(pouchId)
    }
}
