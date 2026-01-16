package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.viewmodel.GardenZoneViewModel

@Composable
fun SelectGardenItemScreen(gardenZoneViewModel: GardenZoneViewModel
) {
    val gardenItemsState = gardenZoneViewModel.getAvailableGardenItems().collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(alpha = 0.5f))
            .clickable {
                gardenZoneViewModel.selectGardenItemsState.value = false
            }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(gardenItemsState.value) { _, gardenItem ->

                Card(
                    modifier = Modifier
                        .padding(30.dp)
                        .clickable {
                            gardenZoneViewModel.setGardenItem(gardenItem)
                        }.background(color = Color.White, shape = RoundedCornerShape(2.dp))
                        .fillMaxSize()
                ) {
                    Column {
                        AsyncImage(
                            model = gardenItem.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                    }

                }
            }
        }

    }
}