package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pro.progr.owlgame.R
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
    val canMoveToAnotherStreet = streets.any { it.id != building.streetId }
    val enterBuilding = { navHostController.navigate("building/${building.id}") }

    Card(modifier = modifier) {
        Column {
            Column(
                modifier = Modifier.clickable {
                    enterBuilding()
                }
            ) {
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
                        text = "Живёт ${it.kind} ${it.name}",
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 4.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
                    .background(MaterialTheme.colors.surface)
                    .padding(horizontal = 4.dp)
            ) {
                IconButton(
                    onClick = { menuExpanded = true },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "Меню домика",
                        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.55f),
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    if (canMoveToAnotherStreet) {

                        DropdownMenuItem(
                            onClick = {
                                menuExpanded = false
                                moveDialogVisible = true
                            }
                        ) {
                            Text("Перенести на улицу")
                        }
                    }
                    DropdownMenuItem(
                        onClick = {
                            menuExpanded = false
                            enterBuilding()
                        }
                    ) {
                        Text("Войти")
                    }
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