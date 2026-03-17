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

        LazyColumn(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),) {

            map.value.expedition?.let { (expedition, enemies) ->
                item {
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
                item {
                    map.value.expedition?.let { expData ->
                        MapOccupiedBanner(
                            expData.expedition.title,
                            expData.expedition.description,
                            { fabExpanded = true}
                        )

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
