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
import pro.progr.owlgame.presentation.ui.SelectGardenItemScreen
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.ui.mapicon.DraggableImageOverlay
import pro.progr.owlgame.presentation.ui.mapicon.gardenItemIconRes
import pro.progr.owlgame.presentation.viewmodel.GardenZoneViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerGardenZoneViewModel

@Composable
fun GardenItems(
    garden: Garden,
    component: OwlGameComponent,
    fabViewModel: FabViewModel
) {
    val vm = DaggerGardenZoneViewModel<GardenZoneViewModel>(component, garden.id, garden.gardenType)
    val items = vm.gardenItems.collectAsState(initial = emptyList())
    fabViewModel.fabActions.value = listOf(
        FabAction(
            text = "Посадить",
            color = Color.DarkGray,
            onClick = {
                vm.selectGardenItemsState.value = true
            }
        )
    )

    val sorted = remember(items.value) {
        items.value
            .sortedWith(compareBy<GardenItem>({ it.x }, { it.id }))
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
                    iconPainterOf = { painterResource(gardenItemIconRes(it.itemType)) },
                    onCommit01 = { item, x, y -> vm.updateGardenItemPos(item.id, x, y) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        items(rows.size) { ind ->
            val row = rows[ind]
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { gi -> GardenItemCard(gi, Modifier.weight(1f)) }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }

    if (vm.selectGardenItemsState.value) {
        SelectGardenItemScreen(vm, fabViewModel)
    }
}

@Composable
private fun GardenItemCard(item: GardenItem, modifier: Modifier = Modifier) {
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
