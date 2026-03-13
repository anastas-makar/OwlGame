package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.State
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.presentation.ui.MapBar
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.mapicon.DraggableImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.enemyIconRes
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun FreeMapScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    mapViewModel: MapViewModel,
    map: State<MapData>
) {
    val foundTown = mapViewModel.foundTown.collectAsState(initial = false)
    val cityName = remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    // Показываем FAB только когда реально можно строить (подстрой под свою логику)
    val showBuildFab =
        map.value.id.isNotEmpty() &&
                //(map.value.type == MapType.TOWN || map.value.type == MapType.FREE) &&
                !foundTown.value

    // Если открылись оверлеи — FAB-меню закрываем
    LaunchedEffect(
        foundTown.value
    ) {
        if (foundTown.value) {
            fabExpanded = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState, modifier = Modifier.navigationBarsPadding()) },
        topBar = { Box(Modifier.statusBarsPadding()) { MapBar(navController, mapViewModel) } },
        floatingActionButton = {
            ExpandableFloatingActionButton(
                expanded = fabExpanded,
                onExpandedChange = { fabExpanded = it },
                actions = listOf(

                    FabAction(
                        text = "Основать город",
                        color = Color.DarkGray,
                        onClick = { mapViewModel.startToFoundTown() }
                    )
                ),
                modifier = Modifier.navigationBarsPadding()
            )
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

                    //todo: переделать на безиконочную
                    Box(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                        DraggableImageOverlay(
                            backgroundModel = map.value.imageUrl,
                            items = emptyList<Any>(),
                            modifier = Modifier.fillMaxWidth(),
                            keyOf = { 0 },
                            x01Of = { 0f },
                            y01Of = { 0f },
                            isNewOf = { false },
                            iconPainterOf = { painterResource(enemyIconRes()) },
                            onCommit01 = { item, x, y ->  },
                        )
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
        }
    }
}

