package pro.progr.owlgame.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pro.progr.diamondapi.DiamondInterface
import pro.progr.owlgame.presentation.ui.model.BuildingModel
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerMapViewModel

@Composable
fun MapScreen(
    navController: NavHostController,
    id: String,
    diamondDao: DiamondInterface,
    mapViewModel: MapViewModel = DaggerMapViewModel(id)
) {
    val map = mapViewModel.map.collectAsState(initial = MapData("", "", ""))
    val foundTown = mapViewModel.foundTown.collectAsState(initial = false)
    val cityName = remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val diamondBalance = diamondDao.getDiamondsCount().collectAsState(initial = 0)

    Log.wtf("Diamond balance: ", diamondBalance.value.toString())

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState,
            modifier = Modifier.navigationBarsPadding()) },
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                MapBar(navController, mapViewModel)
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    if (map.value.id == "") {
                        Text(text = "Загрузка...")
                    } else if (!foundTown.value && map.value.town == null) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = {
                                mapViewModel.startToFoundTown()
                            },
                            modifier = Modifier.align(CenterHorizontally)
                        ) {
                            Text(text = "Основать город")
                        }
                    } else if (map.value.town != null) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.DarkGray,
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    mapViewModel.selectHouseState.value = true
                                },
                                modifier = Modifier.padding(horizontal = 7.dp)
                            ) {
                                Text(text = "Построить дом")
                            }

                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.DarkGray,
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    mapViewModel.selectFortressState.value = true
                                },
                                modifier = Modifier.padding(horizontal = 7.dp)
                            ) {
                                Text(text = "Построить замок")
                            }
                        }
                    }

                    DraggableImages(map, mapViewModel)

                    val buildingsOnMapsState = mapViewModel.getBuildingsOnMap(map.value.id).collectAsState(
                        initial = emptyList()
                    )

                    map.value.town?.let { town ->
                        Text(text = "Улица Главная", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)

                        BuildingsGrid(
                            buildingsList = buildingsOnMapsState
                                .value.map { building ->
                                    BuildingModel(
                                        building.id,
                                        building.name,
                                        building.imageUrl
                                    )
                                },
                            mapViewModel = mapViewModel
                        )
                    }

                }

                if (foundTown.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.Transparent)
                                .padding(16.dp),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = cityName.value,
                                onValueChange = { cityName.value = it },
                                label = { Text(text = "Название города",
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .background(color = Color.White)
                                        .padding(1.dp)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White, // Цвет фона внутри рамки
                                    focusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray // Цвет рамки без фокуса
                                )
                            )

                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.DarkGray,
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    mapViewModel.foundTown(map.value, cityName.value)
                                }
                            ) {
                                Text(text = "Сохранить")
                            }
                        }
                    }
                }

                if (mapViewModel.selectHouseState.value) {
                    SelectHouseScreen(mapViewModel = mapViewModel,
                        diamondBalance = diamondBalance,
                        diamondDao = diamondDao,
                        scope = scope,
                        snackbarHostState = snackbarHostState
                        )
                }

                if (mapViewModel.selectFortressState.value) {
                    SelectHouseScreen(mapViewModel = mapViewModel,
                        diamondBalance = diamondBalance,
                        diamondDao = diamondDao,
                        scope = scope,
                        snackbarHostState = snackbarHostState)
                }
            }
        }
    )
}

@Composable
fun BuildingsGrid(buildingsList : List<BuildingModel>, mapViewModel: MapViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(buildingsList) { _, building ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(building.imageResource)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                mapViewModel.selectHouseState.value = false
                                mapViewModel.selectedBuilding.value = building
                                mapViewModel.newHouseState.value = true
                            }
                    )
                }
            }
        }

    }
}
