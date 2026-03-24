package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import pro.progr.owlgame.data.model.EnemyStatus
import pro.progr.owlgame.presentation.ui.MapBar
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.mapicon.FixedImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.enemyIconRes
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun ExpeditionScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    mapViewModel: MapViewModel,
    map: State<MapData>
) {
    var shouldRun by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(
        shouldRun
    ) {
        if (shouldRun) {
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
                        text = "Бежать",
                        color = Color.DarkGray,
                        onClick = {
                            shouldRun = true
                        }
                    )
                ),
                modifier = Modifier.navigationBarsPadding()
            )
        }
    ) { innerPadding ->

        Box(Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .heightIn(max = 420.dp)) {

            map.value.expedition?.let { (expedition, enemies) ->
                FixedImageOverlay(
                    backgroundModel = map.value.imageUrl,
                    items = enemies,
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
}

