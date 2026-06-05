package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.presentation.ui.MapBar
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.mapicon.FixedImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.enemyIconRes
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.presentation.ui.mapicon.locationIconRes
import pro.progr.owlgame.presentation.ui.model.mapitem.EnemyMapItem
import pro.progr.owlgame.presentation.ui.model.mapitem.OccupiedMapLocationItem
import pro.progr.owlgame.presentation.viewmodel.ExpeditionPreparationViewModel
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.viewmodel.OccupiedMapViewModel

@Composable
fun OccupiedMapScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    mapViewModel: MapViewModel,
    map: State<MapWithDataModel>,
    prepViewModel: ExpeditionPreparationViewModel,
    occupiedMapViewModel: OccupiedMapViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var fabExpanded by rememberSaveable { mutableStateOf(false) }
    var showPreparationDialog by rememberSaveable { mutableStateOf(false) }
    var showAnimalDialog by rememberSaveable { mutableStateOf(false) }
    val failureState by occupiedMapViewModel.uiState.collectAsState()
    val shouldShowFailureDialog = map.value.expedition == null && failureState.shouldShowDialog

    val diamonds by diamondDao.getDiamondsCount().collectAsState(initial = 0)
    val prepState by prepViewModel.uiState.collectAsState()

    val expeditionId = map.value.expedition?.id

    LaunchedEffect(expeditionId) {
        if (expeditionId != null) {
            prepViewModel.ensureAnimalSelected(expeditionId)
        }
    }

    LaunchedEffect(Unit) {
        prepViewModel.events.collect { event ->
            when (event) {
                ExpeditionPreparationViewModel.Event.Started -> {
                    showPreparationDialog = false
                    fabExpanded = false
                }
            }
        }
    }

    LaunchedEffect(prepState.errorMessage) {
        prepState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            prepViewModel.clearError()
        }
    }

    LaunchedEffect(failureState.errorMessage) {
        failureState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            occupiedMapViewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.navigationBarsPadding()
            )
        },
        topBar = {
            Box(Modifier.statusBarsPadding()) {
                MapBar(navController, mapViewModel)
            }
        },
        floatingActionButton = {
            if (prepState.hasAnyPets) {
                ExpandableFloatingActionButton(
                    expanded = fabExpanded,
                    onExpandedChange = { fabExpanded = it },
                    actions = if (prepState.canChooseAnotherPet) listOf(
                        FabAction(
                            text = "Начать экспедицию",
                            color = Color.Red,
                            onClick = {
                                fabExpanded = false
                                showPreparationDialog = true
                            }
                        ),
                        FabAction(
                            text = "Выбрать другое животное",
                            color = Color.DarkGray,
                            onClick = {
                                fabExpanded = false
                                showAnimalDialog = true
                            }
                        )
                    ) else listOf(
                        FabAction(
                            text = "Начать экспедицию",
                            color = Color.Red,
                            onClick = {
                                fabExpanded = false
                                showPreparationDialog = true
                            }
                        )
                    ),
                    modifier = Modifier.navigationBarsPadding()
                )
            }

        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            map.value.expedition?.let { expedition ->
                item {
                    val mapItems = remember(expedition.enemies, map.value.locations) {
                        map.value.locations.map { OccupiedMapLocationItem(it) } +
                                expedition.enemies.map { EnemyMapItem(it) }
                    }

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(max = 420.dp)
                    ) {
                        FixedImageOverlay(
                            backgroundModel = map.value.imageUrl,
                            items = mapItems,
                            modifier = Modifier.fillMaxWidth(),
                            keyOf = { it.id },
                            x01Of = { it.x },
                            y01Of = { it.y },
                            isJumping = { false },
                            iconPainterOf = { item ->
                                when (item) {
                                    is OccupiedMapLocationItem ->
                                        painterResource(locationIconRes(item.location.type))

                                    is EnemyMapItem ->
                                        painterResource(enemyIconRes())
                                }
                            }
                        )
                    }
                }

                item {
                    MapOccupiedBanner(
                        title = expedition.title,
                        description = expedition.description,
                        onClick = {
                            fabExpanded = true
                        }
                    )
                }

                item {
                    ExpeditionAnimalBanner(
                        selectedAnimal = prepState.selectedAnimal,
                        hasAnyPets = prepState.hasAnyPets,
                        canChooseAnotherPet = prepState.canChooseAnotherPet,
                        onClick = {
                            if (prepState.canChooseAnotherPet) {
                                showAnimalDialog = true
                            }
                        }
                    )
                }
            }
        }

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

        if (showPreparationDialog && expeditionId != null) {
            ExpeditionPreparationDialog(
                state = prepState,
                diamondsAvailable = diamonds,
                onDismiss = { showPreparationDialog = false },
                onIncreaseSupply = prepViewModel::increaseSupply,
                onDecreaseSupply = prepViewModel::decreaseSupply,
                onExtraHealChange = prepViewModel::setExtraHealText,
                onExtraDamageChange = prepViewModel::setExtraDamageText,
                onStartClick = {
                    prepViewModel.startExpedition(
                        expeditionId = expeditionId,
                        diamondDao = diamondDao
                    )
                }
            )
        }

        if (showAnimalDialog && expeditionId != null) {
            AnimalSelectionDialog(
                animals = prepState.availablePets,
                selectedAnimalId = prepState.selectedAnimal?.id,
                onDismiss = { showAnimalDialog = false },
                onAnimalClick = { animal ->
                    prepViewModel.selectAnimal(
                        expeditionId = expeditionId,
                        animalId = animal.id
                    )
                    showAnimalDialog = false
                }
            )
        }

        if (shouldShowFailureDialog) {
            ExpeditionFailedDialog(
                reason = failureState.reason,
                animal = failureState.animal!!,
                enemy = failureState.enemy,
                isLoading = failureState.isLoading,
                onRegroupClick = {
                    occupiedMapViewModel.regroupEnemies()
                }
            )
        }
    }
}

@Composable
private fun MapOccupiedBanner(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 10.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
