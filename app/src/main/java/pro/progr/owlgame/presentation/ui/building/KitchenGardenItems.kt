package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.presentation.ui.SelectPlantScreen
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.ui.mapicon.DraggableImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.plantIconRes
import pro.progr.owlgame.presentation.viewmodel.KitchenGardenViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerKitchenGardenViewModel
import kotlin.math.roundToInt

@Composable
fun KitchenGardenItems(
    garden: Garden,
    component: OwlGameComponent,
    fabViewModel: FabViewModel,
    onMap: Boolean = false
) {
    val vm = DaggerKitchenGardenViewModel<KitchenGardenViewModel>(component, garden.id)
    val items = vm.plants.collectAsState(initial = emptyList())
    val availablePlants = vm.availablePlants.collectAsState(initial = emptyList())

    var harvestPlant by remember { mutableStateOf<Plant?>(null) }

    harvestPlant?.let { plant ->
        HarvestPlantDialog(
            plant = plant,
            supplyFlow = vm.observeSupply(plant.supplyId),
            onHarvestSeeds = {
                vm.harvestSeeds(plant)
                harvestPlant = null
            },
            onHarvestSupply = {
                vm.harvestSupply(plant)
                harvestPlant = null
            },
            onDismiss = { harvestPlant = null }
        )
    }

    fabViewModel.fabActions.value = listOf(
        FabAction(
            text = "Посадить растение",
            color = Color.DarkGray,
            onClick = {
                vm.selectPlantState.value = true
            }
        )
    )

    fabViewModel.showFab.value = onMap && availablePlants.value.isNotEmpty()

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
                row.forEach { p -> PlantCard(p,
                    Modifier.weight(1f),
                    onReadyClick = { harvestPlant = it }) }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }

    if (vm.selectPlantState.value) {
        SelectPlantScreen(vm, fabViewModel, availablePlants)
    }
}

@Composable
private fun PlantCard(
    item: Plant,
    modifier: Modifier = Modifier,
    onReadyClick: (Plant) -> Unit
) {
    val r = item.readiness.coerceIn(0f, 1f)
    val ready = r >= 0.999f
    val pct = (r * 100f).roundToInt()

    Card(
        modifier = modifier
            .clickable(enabled = ready) { onReadyClick(item) }
    ) {
        Column {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f)
            )

            Column(Modifier.padding(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item.name, modifier = Modifier.weight(1f))
                    Text(
                        text = if (ready) "Готово" else "$pct%",
                        fontWeight = if (ready) FontWeight.Bold else FontWeight.Normal,
                        color = if (ready) Color(0xFF2E7D32) else Color.Gray
                    )
                }

                Spacer(Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(Color.Black.copy(alpha = 0.10f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(r)
                            .clip(RoundedCornerShape(999.dp))
                            .background(if (ready) Color(0xFF2E7D32) else Color.DarkGray)
                    )
                }
            }
        }
    }
}