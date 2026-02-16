package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.BuildingType
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.mapicon.DraggableImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.buildingIconRes
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

    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    // Показываем FAB только когда реально можно строить (подстрой под свою логику)
    val showBuildFab =
        map.value.id.isNotEmpty() &&
                map.value.type == MapType.TOWN &&
                !foundTown.value &&
                !mapViewModel.selectHouseState.value &&
                !mapViewModel.selectFortressState.value

    // Если открылись оверлеи — FAB-меню закрываем
    LaunchedEffect(
        foundTown.value,
        mapViewModel.selectHouseState.value,
        mapViewModel.selectFortressState.value
    ) {
        if (foundTown.value || mapViewModel.selectHouseState.value || mapViewModel.selectFortressState.value) {
            fabExpanded = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState, modifier = Modifier.navigationBarsPadding()) },
        topBar = { Box(Modifier.statusBarsPadding()) { MapBar(navController, mapViewModel) } },
        floatingActionButton = {
            if (showBuildFab) {
                ExpandableFloatingActionButton(
                    expanded = fabExpanded,
                    onExpandedChange = { fabExpanded = it },
                    actions = listOf(
                        FabAction(
                            text = "Построить дом",
                            color = Color.DarkGray,
                            onClick = { mapViewModel.selectHouseState.value = true }
                        ),
                        FabAction(
                            text = "Построить замок",
                            color = Color.DarkGray,
                            onClick = { mapViewModel.selectFortressState.value = true }
                        )
                    ),
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    ) { innerPadding ->

        Box(Modifier.fillMaxSize()) {

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 1) Хедер
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
                            // Раньше тут были кнопки Дом/Замок — теперь ничего
                            Spacer(Modifier.height(0.dp))
                        }
                    }
                }

                item {
                    LaunchedEffect(
                        mapViewModel.newHouseState.value,
                        mapViewModel.selectedBuilding.value?.id,
                        map.value.id
                    ) {
                        val selected = mapViewModel.selectedBuilding.value
                        if (mapViewModel.newHouseState.value && selected != null && map.value.id.isNotEmpty()) {
                            mapViewModel.saveSlot(
                                x = 0f,
                                y = 0f,
                                mapId = map.value.id,
                                buildingId = selected.id
                            )
                        }
                    }

                    Box(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                        DraggableImageOverlay(
                            backgroundModel = map.value.imageUrl,
                            items = map.value.buildings,
                            modifier = Modifier.fillMaxWidth(),
                            keyOf = { it.building.id },
                            x01Of = { it.building.x },
                            y01Of = { it.building.y },
                            isNewOf = { it.building.x == 0f && it.building.y == 0f},
                            iconPainterOf = { painterResource(buildingIconRes(it.building.type)) },
                            onCommit01 = { item, x, y -> mapViewModel.updateSlot(item.building.id, x, y) },
                        )
                    }
                }

                if (map.value.type == MapType.TOWN) {
                    item {
                        Text(
                            text = "Улица Главная",
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    val buildings = map.value.buildings.map { s ->
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
                                BuildingCard(b, Modifier.weight(1f), navController)
                            }
                            repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
                        }
                    }
                }
            }

            // Scrim для FAB-меню (закрывать по тапу вне)
            if (fabExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.35f))
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = { fabExpanded = false })
                        }
                )
            }

            // Оверлеи (как у тебя было)
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
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BuildingCard(building: BuildingModel, modifier: Modifier = Modifier,
                         navHostController: NavHostController) {
    Card(modifier = modifier, onClick = {navHostController.navigate("building/${building.id}")}) {
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
                Text("Живёт ${it.kind} ${it.name}", modifier = Modifier.padding(5.dp))
            }
        }
    }
}
