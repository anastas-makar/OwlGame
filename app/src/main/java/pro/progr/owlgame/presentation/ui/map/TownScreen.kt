package pro.progr.owlgame.presentation.ui.map

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import pro.progr.owlgame.domain.model.BuildingType
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.presentation.ui.MapBar
import pro.progr.owlgame.presentation.ui.SelectBuildingScreen
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.mapicon.DraggableImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.buildingIconRes
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.model.StreetDirection
import pro.progr.owlgame.domain.model.StreetWithBuildingsModel
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun TownScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    mapViewModel: MapViewModel,
    map: State<MapWithDataModel>
) {
    val foundTown = mapViewModel.foundTown.collectAsState(initial = false)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val diamondBalance = diamondDao.getDiamondsCount().collectAsState(initial = 0)


    val streetOptions = remember(map.value.streets) {
        val hasMain = map.value.streets.any { it.isMain }

        if (hasMain) {
            map.value.streets
        } else {
            listOf(
                StreetWithBuildingsModel(
                    id = null,
                    name = "Улица Главная",
                    direction = StreetDirection.WEST_TO_EAST,
                    isMain = true,
                    buildings = emptyList()
                )
            ) + map.value.streets
        }
    }

    var showCreateStreetDialog by rememberSaveable { mutableStateOf(false) }

    if (showCreateStreetDialog) {
        CreateStreetDialog(
            onDismiss = { showCreateStreetDialog = false },
            onCreate = { name, direction ->
                showCreateStreetDialog = false
                mapViewModel.createStreet(
                    mapId = map.value.id,
                    name = name,
                    direction = direction
                )
            }
        )
    }

    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    // Показываем FAB только когда реально можно строить (подстрой под свою логику)
    val showBuildFab =
        map.value.id.isNotEmpty() &&
                //(map.value.type == MapType.TOWN || map.value.type == MapType.FREE) &&
                !foundTown.value &&
                !mapViewModel.selectHouseState.value &&
                !mapViewModel.selectFortressState.value

    // Если открылись оверлеи — FAB-меню закрываем
    LaunchedEffect(
        mapViewModel.selectHouseState.value,
        mapViewModel.selectFortressState.value
    ) {
        if (mapViewModel.selectHouseState.value || mapViewModel.selectFortressState.value) {
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
                        ),
                        FabAction(
                            text = "Создать улицу",
                            color = Color.DarkGray,
                            onClick = { showCreateStreetDialog = true }
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
                            keyOf = { it.id },
                            x01Of = { it.x },
                            y01Of = { it.y },
                            isNewOf = { it.x == 0f && it.y == 0f},
                            iconPainterOf = { painterResource(buildingIconRes(it.type)) },
                            onCommit01 = { item, x, y -> mapViewModel.updateSlot(item.id, x, y) },
                        )
                    }
                }

                map.value.streets.forEach { street ->
                    item {
                        StreetHeader(
                            street = street,
                            onDeleteStreet = {
                                street.id?.let { mapViewModel.deleteStreet(it) }
                            }
                        )
                    }

                    if (street.buildings.isEmpty()) {
                        item {
                            Text(
                                text = "Здесь пока нет домов. Перенесите сюда дома из меню домика.",
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    } else {

                        val rows = street.buildings.chunked(3)

                        items(rows) { row ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                row.forEach { building ->
                                    TownBuildingCard(
                                        building = building,
                                        modifier = Modifier.weight(1f),
                                        navHostController = navController,
                                        streets = streetOptions,
                                        onMoveToStreet = { buildingId, streetId ->
                                            mapViewModel.moveBuildingToStreet(buildingId, streetId)
                                        }
                                    )
                                }

                                repeat(3 - row.size) {
                                    Spacer(Modifier.weight(1f))
                                }
                            }
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

            // Оверлеи

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
private fun BuildingCard(building: BuildingWithAnimalModel, modifier: Modifier = Modifier,
                         navHostController: NavHostController) {
    Card(modifier = modifier, onClick = {navHostController.navigate("building/${building.id}")}) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(building.imageUrl)
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
