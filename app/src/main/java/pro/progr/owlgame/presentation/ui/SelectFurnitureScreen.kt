package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.viewmodel.RoomViewModel

@Composable
fun SelectFurnitureScreen(roomViewModel: RoomViewModel,
                      fabViewModel: FabViewModel
) {
    fabViewModel.showFab.value = false
    val furnitureItemsState = roomViewModel.getAvailableFurnitureItems().collectAsState(initial = emptyList())
    val density = LocalDensity.current
    var bgSizePx by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(alpha = 0.5f))
            .clickable {
                fabViewModel.showFab.value = true
                roomViewModel.selectFurnitureItemState.value = false
            }
            .onSizeChanged { bgSizePx = it }
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
                        }.background(color = Color.Transparent, shape = RoundedCornerShape(2.dp))
                        .height(200.dp)
                ) {

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)) {
                        AsyncImage(
                            model = furniture.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.TopCenter,
                            modifier = Modifier
                                .widthIn(max = with(density) { (bgSizePx.width * furniture.width).toDp() })
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(10.dp)
                                .wrapContentHeight()
                                .align(Alignment.BottomCenter)
                        ) {
                            Text(
                                text = " ◆ ${furniture.price} ",
                                style = MaterialTheme.typography.body1
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_diamond_bright),
                                contentDescription = "Diamond",
                                modifier = Modifier.height(12.dp)
                            )
                        }

                    }

                }
            }
        }

    }
}