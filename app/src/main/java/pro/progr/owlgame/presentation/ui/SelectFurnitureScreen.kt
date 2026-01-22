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
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.viewmodel.RoomViewModel

@Composable
fun SelectFurnitureScreen(roomViewModel: RoomViewModel,
                      fabViewModel: FabViewModel
) {
    fabViewModel.showFab.value = false
    val furnitureItemsState = roomViewModel.getAvailableFurnitureItems().collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(alpha = 0.5f))
            .clickable {
                fabViewModel.showFab.value = true
                roomViewModel.selectFurnitureItemState.value = false
            }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(furnitureItemsState.value) { _, furniture ->

                Card(
                    modifier = Modifier
                        .padding(30.dp)
                        .clickable {
                            fabViewModel.showFab.value = true
                            roomViewModel.setFurnitureItem(furniture)
                        }.background(color = Color.White, shape = RoundedCornerShape(2.dp))
                        .fillMaxSize()
                ) {
                    Column {
                        AsyncImage(
                            model = furniture.imageUrl,
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