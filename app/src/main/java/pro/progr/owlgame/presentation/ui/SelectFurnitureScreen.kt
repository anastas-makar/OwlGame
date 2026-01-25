package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.SnackbarHostState
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.viewmodel.RoomViewModel

@Composable
fun SelectFurnitureScreen(roomViewModel: RoomViewModel,
                      fabViewModel: FabViewModel,
                      diamondDao : PurchaseInterface,
                      scope : CoroutineScope,
                      snackbarHostState: SnackbarHostState
) {
    fabViewModel.showFab.value = false
    val furnitureItemsState = roomViewModel.getAvailableFurnitureItems().collectAsState(initial = emptyList())
    val density = LocalDensity.current
    var bgSizePx by remember { mutableStateOf(IntSize.Zero) }

    val diamondBalance = diamondDao.getDiamondsCount().collectAsState(initial = 0)

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
                            if (diamondBalance.value >= furniture.price) {
                                fabViewModel.showFab.value = true
                                roomViewModel.setFurnitureItem(furniture, diamondDao)
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Недостаточно бриллиантов")
                                }
                            }
                        }.background(color = Color.Transparent, shape = RoundedCornerShape(2.dp))
                        .height(200.dp)
                ) {

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(bottom = 36.dp),  // место под Row с ценой
                            contentAlignment = Alignment.TopCenter
                        ) {
                            AsyncImage(
                                model = furniture.imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    // Центрирование работает, когда у элемента есть "пространство"
                                    // и ограничение по ширине остаётся
                                    .widthIn(max = with(density) { (bgSizePx.width * furniture.width).toDp() })
                                    .fillMaxWidth()
                                    // чтобы совсем узкие не расползались в высоту бесконечно:
                                    .fillMaxHeight()
                            )
                        }

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
                                tint = if (diamondBalance.value < furniture.price) Color.DarkGray
                                else Color.Unspecified,
                                modifier = Modifier.height(12.dp)
                            )
                        }

                    }

                }
            }
        }

    }
}