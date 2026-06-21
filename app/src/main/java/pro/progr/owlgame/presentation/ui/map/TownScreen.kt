package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.model.BuildingType
import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.model.StreetDirection
import pro.progr.owlgame.domain.model.StreetWithBuildingsModel
import pro.progr.owlgame.presentation.ui.MapBar
import pro.progr.owlgame.presentation.ui.SelectBuildingScreen
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.mapicon.DraggableImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.buildingIconRes
import pro.progr.owlgame.presentation.ui.mapicon.locationIconRes
import pro.progr.owlgame.presentation.ui.model.mapitem.TownBuildingMapItem
import pro.progr.owlgame.presentation.ui.model.mapitem.TownLocationMapItem
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

    var showMayorDialog by rememberSaveable {
        mutableStateOf(false)
    }

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

    var selectedLocation by remember { mutableStateOf<LocationWithScenesModel?>(null) }

    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    // Показываем FAB только когда реально можно строить (подстрой под свою логику)
    val showBuildFab =
        map.value.id.isNotEmpty() &&
                //(map.value.type == MapType.TOWN || map.value.type == MapType.FREE) &&
                !foundTown.value &&
                !mapViewModel.selectHouseState.value &&
                !mapViewModel.selectFortressState.value &&
                !mapViewModel.selectLocationState.value

    // Если открылись оверлеи — FAB-меню закрываем
    LaunchedEffect(
        mapViewModel.selectHouseState.value,
        mapViewModel.selectFortressState.value
    ) {
        if (mapViewModel.selectHouseState.value
            || mapViewModel.selectFortressState.value
            || mapViewModel.selectLocationState.value) {
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
                        ),
                        FabAction(
                            text = "Добавить достопримечательность",
                            color = Color.DarkGray,
                            onClick = { mapViewModel.selectLocationState.value = true }
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

                    LaunchedEffect(
                        mapViewModel.newLocationState.value,
                        mapViewModel.selectedLocation.value?.id,
                        map.value.id
                    ) {
                        val selected = mapViewModel.selectedLocation.value
                        if (mapViewModel.newLocationState.value && selected != null && map.value.id.isNotEmpty()) {
                            mapViewModel.saveLocationSlot(
                                x = 0f,
                                y = 0f,
                                mapId = map.value.id,
                                locationId = selected.id
                            )
                        }
                    }

                    val townMapItems = remember(map.value.buildings, map.value.locations) {
                        map.value.locations.map { TownLocationMapItem(it) } +
                                map.value.buildings.map { TownBuildingMapItem(it) }
                    }

                    Box(Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp)) {
                        DraggableImageOverlay(
                            backgroundModel = map.value.imageUrl,
                            items = townMapItems,
                            modifier = Modifier.fillMaxWidth(),
                            keyOf = { it.id },
                            x01Of = { it.x },
                            y01Of = { it.y },
                            isNewOf = { it.x == 0f && it.y == 0f},
                            iconPainterOf = { item ->
                                when (item) {
                                    is TownBuildingMapItem ->
                                        painterResource(buildingIconRes(item.building.type))

                                    is TownLocationMapItem ->
                                        painterResource(locationIconRes(item.location.type))
                                }
                            },
                            onCommit01 = { item, x, y ->
                                when (item) {
                                    is TownBuildingMapItem ->
                                        mapViewModel.updateSlot(item.id, x, y)

                                    is TownLocationMapItem ->
                                        mapViewModel.updateLocationSlot(item.id, x, y)
                                }
                            },
                        )
                    }
                }

                item {
                    TownMayorCard(
                        map = map.value,
                        onAppointMayor = {
                            showMayorDialog = true
                        },
                        onRemoveMayor = {
                            mapViewModel.removeMayor(map.value.id)
                        }
                    )
                }

                locationsSection(
                    locations = map.value.locations,
                    onLocationClick = { selectedLocation = it }
                )

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

            if (mapViewModel.selectLocationState.value) {
                SelectLocationScreen(
                    mapViewModel = mapViewModel,
                    diamondBalance = diamondBalance,
                    diamondDao = diamondDao,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    onDismiss = {
                        mapViewModel.selectLocationState.value = false
                    }
                )
            }
        }

        selectedLocation?.let { location ->
            LocationGalleryDialog(
                location = location,
                onDismiss = { selectedLocation = null }
            )
        }

        if (showMayorDialog) {
            AppointMayorDialog(
                map = map.value,
                candidates = map.value.getMayorCandidates(),
                onDismiss = {
                    showMayorDialog = false
                },
                onAppoint = { animalId ->
                    showMayorDialog = false
                    mapViewModel.appointMayor(
                        mapId = map.value.id,
                        animalId = animalId
                    )
                }
            )
        }
    }
}
