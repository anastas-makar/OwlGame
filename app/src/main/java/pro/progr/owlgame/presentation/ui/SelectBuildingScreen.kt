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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingType
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun SelectBuildingScreen(mapViewModel: MapViewModel,
                         diamondBalance : State<Int>,
                         diamondDao : PurchaseInterface,
                         scope : CoroutineScope,
                         snackbarHostState: SnackbarHostState,
                         buildingType: BuildingType
) {
    val buildingsState = mapViewModel.getAvailableBuildings().collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(alpha = 0.5f))
            .clickable {
                mapViewModel.selectHouseState.value = false
                mapViewModel.selectFortressState.value = false
            }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(buildingsState.value.filter {building: Building ->
                building.type == buildingType
            }) { _, building ->

                Card(
                    modifier = Modifier
                        .padding(30.dp)
                        .clickable {
                            if (diamondBalance.value >= building.price) {
                                mapViewModel.purchase(diamondDao, building)
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Недостаточно бриллиантов")
                                }
                            }
                        }.background(color = Color.White, shape = RoundedCornerShape(2.dp))
                        .fillMaxSize()
                ) {
                    Column {
                        AsyncImage(
                            model = building.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(10.dp)
                                .wrapContentHeight()
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = " ◆ ${building.price} ",
                                style = MaterialTheme.typography.body1
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_diamond_bright),
                                contentDescription = "Diamond",
                                tint = if (diamondBalance.value < building.price) Color.DarkGray
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