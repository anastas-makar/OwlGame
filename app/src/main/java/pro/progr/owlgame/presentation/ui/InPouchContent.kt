package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
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
import pro.progr.owlgame.data.web.Pouch
import pro.progr.owlgame.presentation.ui.model.InPouchDescription
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel

@Composable
fun InPouchContent(
    navController: NavHostController,
    inPouchViewModel: InPouchViewModel,
    pouch: Pouch
) {
    inPouchViewModel.inPouch.value?.let { inPouch ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            item {
                //Описание того, что есть в мешочке
                Row {
                    AsyncImage(
                        model = pouch.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(200.dp)
                            .aspectRatio(1f)
                    )
                    Text(text = InPouchDescription(inPouch).compile())
                }
            }

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
                Card (modifier = Modifier.padding(16.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = map.name,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
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
}
