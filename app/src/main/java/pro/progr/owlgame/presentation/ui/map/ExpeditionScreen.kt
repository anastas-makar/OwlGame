package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.presentation.ui.MapBar
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.mapicon.FixedImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.enemyIconRes
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.presentation.ui.model.ExpeditionCreatureDetails
import pro.progr.owlgame.presentation.viewmodel.ExpeditionScreenViewModel
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun ExpeditionScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    mapViewModel: MapViewModel,
    expeditionScreenViewModel: ExpeditionScreenViewModel,
    map: State<MapWithDataModel>
) {
    var shouldRun by rememberSaveable { mutableStateOf(false) }
    var fabExpanded by rememberSaveable { mutableStateOf(false) }
    var detailsTarget by rememberSaveable { mutableStateOf<ExpeditionCreatureDetails?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val battleState by expeditionScreenViewModel.uiState.collectAsState()

    val expeditionId = map.value.expedition?.id

    LaunchedEffect(expeditionId) {
        if (expeditionId != null) {
            mapViewModel.resolveExpeditionProgress(expeditionId)
        }
    }

    LaunchedEffect(shouldRun) {
        if (shouldRun) {
            fabExpanded = false
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
            ExpandableFloatingActionButton(
                expanded = fabExpanded,
                onExpandedChange = { fabExpanded = it },
                actions = listOf(
                    FabAction(
                        text = "Бежать!",
                        color = Color.Red,
                        onClick = {
                            shouldRun = true
                        }
                    )
                ),
                modifier = Modifier.navigationBarsPadding()
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp)
                ) {
                    battleState.expedition?.let { expedition ->
                        FixedImageOverlay(
                            backgroundModel = map.value.imageUrl,
                            items = expedition.enemies,
                            modifier = Modifier.fillMaxWidth(),
                            keyOf = { it.id },
                            x01Of = { it.x },
                            y01Of = { it.y },
                            isJumping = { it.status == EnemyStatus.ACTIVE },
                            iconPainterOf = { painterResource(enemyIconRes()) }
                        )
                    }
                }
            }

            item {
                BattleHeaderRow(
                    animal = battleState.animal,
                    expedition = battleState.expedition,
                    activeEnemy = battleState.activeEnemy,
                    onAnimalClick = {
                        val animal = battleState.animal
                        val expedition = battleState.expedition
                        if (animal != null && expedition != null) {
                            detailsTarget = ExpeditionCreatureDetails.AnimalDetails(animal, expedition)
                        }
                    },
                    onActiveEnemyClick = {
                        battleState.activeEnemy?.let { enemy ->
                            detailsTarget = ExpeditionCreatureDetails.EnemyDetails(enemy)
                        }
                    }
                )
            }

            item {
                EnemyGalleryRow(
                    enemies = battleState.enemies,
                    onEnemyClick = { enemy ->
                        detailsTarget = ExpeditionCreatureDetails.EnemyDetails(enemy)
                    }
                )
            }
        }

        detailsTarget?.let { target ->
            ExpeditionCreatureDialog(
                target = target,
                onDismiss = { detailsTarget = null }
            )
        }
    }
}

