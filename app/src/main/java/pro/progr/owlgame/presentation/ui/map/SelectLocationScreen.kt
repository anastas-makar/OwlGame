package pro.progr.owlgame.presentation.ui.map

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SelectLocationScreen(
    mapViewModel: MapViewModel,
    diamondBalance: State<Int>,
    diamondDao: PurchaseInterface,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit
) {
    val locations = mapViewModel.getAvailableLocations()
        .collectAsState(initial = emptyList())

    BackHandler {
        onDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.94f))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Выберите достопримечательность",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.weight(1f)
                    )

                    TextButton(onClick = onDismiss) {
                        Text("Закрыть")
                    }
                }
            }

            items(locations.value) { location ->
                LocationPurchaseCard(
                    location = location,
                    canBuy = diamondBalance.value >= location.price,
                    onClick = {
                        if (diamondBalance.value >= location.price) {
                            mapViewModel.purchaseLocation(diamondDao, location)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Не хватает бриллиантов")
                            }
                        }
                    }
                )
            }
        }
    }
}