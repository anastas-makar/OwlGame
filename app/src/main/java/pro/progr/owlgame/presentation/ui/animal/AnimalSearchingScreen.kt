package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.presentation.ui.model.SelectedBuilding
import pro.progr.owlgame.presentation.viewmodel.AnimalSearchingEvent
import pro.progr.owlgame.presentation.viewmodel.AnimalViewModel

@Composable
fun AnimalSearchingScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    animalId: String,
    animalViewModel: AnimalViewModel
) {
    val animalState = animalViewModel.animal.collectAsState(initial = null)
    val mapsState = animalViewModel.mapsWithFreeBuildings.collectAsState(initial = emptyList())
    val isBusyState = animalViewModel.isBusy.collectAsState()

    var selectedBuilding by remember { mutableStateOf<SelectedBuilding?>(null) }
    var showSendAwayDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animalViewModel.events.collect { event ->
            when (event) {
                is AnimalSearchingEvent.OpenMap -> {
                    navController.navigate("map/${event.mapId}")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                AnimalSearchingBar(backToMain, animalState)
            }
        }
    ) { innerPadding ->

        val animal = animalState.value

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                animal == null -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                animal.status == AnimalStatus.GONE -> {
                    AnimalGoneContent(
                        animal = animal,
                        backToMain = backToMain
                    )
                }

                animal.status != AnimalStatus.SEARCHING -> {
                    AnimalAlreadyHasHomeContent(
                        animal = animal,
                        backToMain = backToMain
                    )
                }

                else -> {
                    AnimalHomeSelectionContent(
                        animal = animal,
                        maps = mapsState.value,
                        isBusy = isBusyState.value,
                        onBuildingClick = { building, mapId ->
                            selectedBuilding = SelectedBuilding(
                                buildingId = building.id,
                                buildingName = building.name,
                                mapId = mapId
                            )
                        },
                        onSendAwayClick = {
                            showSendAwayDialog = true
                        }
                    )
                }
            }
        }
    }

    selectedBuilding?.let { building ->
        GiveNameAndSettleDialog(
            animal = animalState.value,
            buildingName = building.buildingName,
            isBusy = isBusyState.value,
            onDismiss = {
                selectedBuilding = null
            },
            onConfirm = { newName ->
                animalViewModel.settleAnimalInBuilding(
                    buildingId = building.buildingId,
                    mapId = building.mapId,
                    newAnimalName = newName
                )
                selectedBuilding = null
            }
        )
    }

    if (showSendAwayDialog) {
        SendAnimalAwayDialog(
            isBusy = isBusyState.value,
            onDismiss = {
                showSendAwayDialog = false
            },
            onConfirm = {
                animalViewModel.sendAnimalAway()
                showSendAwayDialog = false
            }
        )
    }
}