package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.BuildingType
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.presentation.ui.model.BuildingModel
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun MapScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    mapViewModel: MapViewModel
) {
    val map = mapViewModel.map.collectAsState(initial = MapData("", "", "", MapType.FREE))
    val foundTown = mapViewModel.foundTown.collectAsState(initial = false)
    val cityName = remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val diamondBalance = diamondDao.getDiamondsCount().collectAsState(initial = 0)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState, modifier = Modifier.navigationBarsPadding()) },
        topBar = { Box(Modifier.statusBarsPadding()) { MapBar(navController, mapViewModel) } },
        content = { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 1) Хедер/кнопки
                item {
                    when {
                        map.value.id.isEmpty() -> Text("Загрузка…")
                        !foundTown.value && map.value.type == MapType.FREE -> {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.DarkGray, contentColor = Color.White
                                ),
                                onClick = { mapViewModel.startToFoundTown() },
                                modifier = Modifier.wrapContentSize()
                            ) { Text("Основать город") }
                        }
                        else -> {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.DarkGray, contentColor = Color.White
                                    ),
                                    onClick = { mapViewModel.selectHouseState.value = true },
                                    modifier = Modifier.weight(1f)
                                ) { Text("Построить дом") }

                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.DarkGray, contentColor = Color.White
                                    ),
                                    onClick = { mapViewModel.selectFortressState.value = true },
                                    modifier = Modifier.weight(1f)
                                ) { Text("Построить замок") }
                            }
                        }
                    }
                }

                // 2) Карта/дрэг-область — ДАЙ ЕЙ ГРАНИЦУ ПО ВЫСОТЕ!
                item {
                    // если DraggableImages тянет высоту, ограничь:
                    Box(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                        DraggableImages(map, mapViewModel)
                    }
                }

                // 3) Заголовок улицы
                if (map.value.type == MapType.TOWN) {
                    item {
                        Text(
                            text = "Улица Главная",
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // 4) Сетка как строки по 3
                    val buildings = map.value.buildings
                        .map { s ->
                            BuildingModel(
                                s.building.id,
                                s.building.name,
                                s.building.imageUrl,
                                s.animal
                            )
                        }
                    val rows = buildings.chunked(3)

                    items(rows) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            row.forEach { b ->
                                BuildingCard(b, Modifier.weight(1f))
                            }
                            // добиваем пустые ячейки чтобы сетка была ровной
                            repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
                        }
                    }
                }
            }

            // Оверлеи поверх — оставляем как было:
            if (foundTown.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = cityName.value,
                            onValueChange = { cityName.value = it },
                            label = {
                                Text(
                                    text = "Название города",
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .background(Color.White)
                                        .padding(1.dp)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = Color.White,
                                focusedBorderColor = Color.Gray,
                                unfocusedLabelColor = Color.Gray,
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray, contentColor = Color.White
                            ),
                            onClick = { mapViewModel.foundTown(map.value, cityName.value) }
                        ) { Text("Сохранить") }
                    }
                }
            }

            if (mapViewModel.selectHouseState.value) {
                SelectBuildingScreen(
                    mapViewModel = mapViewModel,
                    diamondBalance = diamondBalance,
                    diamondDao = diamondDao,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    buildingType = BuildingType.HOUSE
                )
            }
            if (mapViewModel.selectFortressState.value) {
                SelectBuildingScreen(
                    mapViewModel = mapViewModel,
                    diamondBalance = diamondBalance,
                    diamondDao = diamondDao,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    buildingType = BuildingType.FORTRESS
                )
            }
        }
    )
}

@Composable
private fun BuildingCard(building: BuildingModel, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(building.imageResource)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            building.animal?.let {
                Text("Живёт ${it.name}", modifier = Modifier.padding(5.dp))
            }
        }
    }
}
