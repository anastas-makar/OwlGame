package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.input.pointer.pointerInput
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
fun OccupiedMapScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    mapViewModel: MapViewModel,
    map: State<MapData>
) {

    val snackbarHostState = remember { SnackbarHostState() }

    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    // Если открылись оверлеи — FAB-меню закрываем
    LaunchedEffect(
        mapViewModel.newExpeditionState.value
    ) {
        if (mapViewModel.newExpeditionState.value) {
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
                    actions =
                            listOf(

                                FabAction(
                                    text = "Начать экспедицию",
                                    color = Color.Red,
                                    onClick = {  }
                                )
                            ),
                    modifier = Modifier.navigationBarsPadding()
                )
        }
    ) { innerPadding ->

        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),) {

            map.value.expedition?.let { (expedition, enemies) ->

                //todo: иконки монстров двигать нельзя, нужно переделать на статичные иконки
                    Box(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                        FixedImageOverlay(
                            backgroundModel = map.value.imageUrl,
                            items = enemies,
                            modifier = Modifier.fillMaxWidth(),
                            keyOf = { it.id },
                            x01Of = { it.x },
                            y01Of = { it.y },
                            isJumping = { it.status == EnemyStatus.ACTIVE},
                            iconPainterOf = { painterResource(enemyIconRes()) }
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
        }
}
