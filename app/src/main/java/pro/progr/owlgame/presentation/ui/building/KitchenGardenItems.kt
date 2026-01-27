package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.presentation.ui.SelectPlantScreen
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.ui.mapicon.DraggableImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.plantIconRes
import pro.progr.owlgame.presentation.viewmodel.KitchenGardenViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerKitchenGardenViewModel

@Composable
fun KitchenGardenItems(
    garden: Garden,
    component: OwlGameComponent,
    fabViewModel: FabViewModel,
    onMap: Boolean = false
) {
    val vm = DaggerKitchenGardenViewModel<KitchenGardenViewModel>(component, garden.id)
    val items = vm.plants.collectAsState(initial = emptyList())
    fabViewModel.fabActions.value = listOf(
        FabAction(
            text = "Посадить растение",
            color = Color.DarkGray,
            onClick = {
                vm.selectPlantState.value = true
            }
        )
    )

    fabViewModel.showFab.value = onMap && vm.availablePlants.value.isNotEmpty()

    val sorted = remember(items.value) {
        items.value
            .sortedWith(compareBy<Plant>({ it.x }, { it.id }))
    }
    val rows = remember(sorted) { sorted.chunked(3) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Box(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                DraggableImageOverlay(
                    backgroundModel = garden.imageUrl,
                    items = sorted,
                    keyOf = { it.id },
                    x01Of = { it.x },
                    y01Of = { it.y },
                    isNewOf = { it.x == 0f && it.y == 0f},
                    iconPainterOf = { painterResource(plantIconRes()) },
                    onCommit01 = { item, x, y -> vm.updatePlantPos(item.id, x, y) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        items(rows.size) { ind ->
            val row = rows[ind]
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { p -> PlantCard(p, Modifier.weight(1f)) }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }

    if (vm.selectPlantState.value) {
        SelectPlantScreen(vm, fabViewModel)
    }
}

@Composable
private fun PlantCard(item: Plant, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f)
            )
            Text(item.name, modifier = Modifier.padding(6.dp))
        }
    }
}
