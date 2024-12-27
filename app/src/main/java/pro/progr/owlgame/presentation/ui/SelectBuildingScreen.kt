package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun SelectBuildingScreen(mapViewModel: MapViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(alpha = 0.5f))
            .clickable {
                mapViewModel.selectHouseState.value = false
            }
    ) {

    }
}