package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.StreetWithBuildingsModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TownBuildingCard(
    building: BuildingWithAnimalModel,
    streets: List<StreetWithBuildingsModel>,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onMoveToStreet: (buildingId: String, streetId: String?) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var moveDialogVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        onClick = { navHostController.navigate("building/${building.id}") }
    ) {
        Box {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(building.imageUrl)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )

                building.animal?.let {
                    Text(
                        "Живёт ${it.kind} ${it.name}",
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }

            IconButton(
                onClick = { menuExpanded = true },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Меню домика"
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        moveDialogVisible = true
                    }
                ) {
                    Text("Перенести на улицу")
                }
            }
        }
    }

    if (moveDialogVisible) {
        MoveBuildingDialog(
            building = building,
            streets = streets,
            onDismiss = { moveDialogVisible = false },
            onMove = { streetId ->
                moveDialogVisible = false
                onMoveToStreet(building.id, streetId)
            }
        )
    }
}