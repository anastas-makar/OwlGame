package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.model.FurnitureType
import pro.progr.owlgame.domain.model.RoomModel
import pro.progr.owlgame.presentation.ui.SelectFurnitureScreen
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.viewmodel.RoomViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerRoomViewModel
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun InRoom(
    room: RoomModel,
    component: OwlGameComponent,
    fabViewModel: FabViewModel,
    diamondDao: PurchaseInterface,
    animal: AnimalModel?,
    onOpenCraft: (animalId: String) -> Unit,
    onMap: Boolean = false,
) {
    val roomViewModel = DaggerRoomViewModel<RoomViewModel>(component, room.id)
    val furniture = roomViewModel.furnitureItems.collectAsState(initial = emptyList())
    val availableFurniture = roomViewModel.availableFurnitureItems.collectAsState(initial = emptyList())

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fabViewModel.fabActions.value = listOf(
        FabAction(
            text = stringResource(R.string.place_furniture),
            color = Color.DarkGray,
            onClick = {
                roomViewModel.selectFurnitureItemState.value = true
            }
        )
    )

    fabViewModel.showFab.value = onMap && availableFurniture.value.isNotEmpty()

    val hasRefrigerator = furniture.value.any { it.type == FurnitureType.REFRIGERATOR }
    val canOpenCraft = hasRefrigerator && animal != null && animal.status == AnimalStatus.PET
    val craftBlocked = hasRefrigerator && animal != null && animal.status != AnimalStatus.PET
    val hasRefrigeratorButNoAnimal = hasRefrigerator && animal == null

    var selectedFurniture by remember {
        mutableStateOf<FurnitureModel?>(null)
    }

    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            DraggableSizedImageOverlay(
                backgroundModel = room.imageUrl,
                items = furniture.value,
                keyOf = { it.id },
                x01Of = { it.x },
                y01Of = { it.y },
                width01Of = { it.width },
                height01Of = { it.height },
                itemImageModelOf = { it.imageUrl },
                isNewOf = { it.x == 0f && it.y == 0f },
                onCommit01 = { f, x, y -> roomViewModel.updatePos(f.id, x, y) },
                defaultWidth01 = 0.22f,
                defaultHeight01 = 0.35f,
                modifier = Modifier.fillMaxWidth(),
                onItemClick = { selectedFurniture = it }
            )
        }

        if (canOpenCraft) {
            CraftAvailableBanner(
                animalKind = animal.kind,
                animalName = animal.displayName,
                onClick = {
                    onOpenCraft(animal.id)
                }
            )
        }

        if (craftBlocked) {
            val reasonRes = when (animal.status) {
                AnimalStatus.FUGITIVE -> R.string.craft_unavailable_animal_fugitive
                AnimalStatus.EXPEDITION -> R.string.craft_unavailable_animal_expedition
                else -> R.string.craft_unavailable_animal_unknown
            }
            CraftNotAvailableBanner(
                stringResource(reasonRes, animal.kind, animal.name.orEmpty())
            )
        }

        if (hasRefrigeratorButNoAnimal) {
            CraftNotAvailableBanner(
                stringResource(R.string.craft_unavailable_no_resident)
            )
        }
    }

    selectedFurniture?.let { item ->
        AlertDialog(
            onDismissRequest = { selectedFurniture = null },
            title = { Text(item.name) },
            text = {
                Column {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = item.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )

                    Text(
                        text = stringResource(R.string.furniture_reinstall_free),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        roomViewModel.removeFurnitureFromRoom(item.id)
                        selectedFurniture = null
                    }
                ) {
                    Text(stringResource(R.string.remove_from_room))
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedFurniture = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    if (roomViewModel.selectFurnitureItemState.value) {
        SelectFurnitureScreen(
            roomViewModel,
            fabViewModel,
            diamondDao,
            scope,
            snackbarHostState,
            availableFurniture
        )
    }
}
