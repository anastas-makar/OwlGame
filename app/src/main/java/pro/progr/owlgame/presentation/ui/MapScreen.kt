package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerMapViewModel

@Composable
fun MapScreen(
    navController: NavHostController,
    id: String,
    mapViewModel: MapViewModel = DaggerMapViewModel(id)
) {
    val map = mapViewModel.map.collectAsState(initial = Map("", "", ""))
    val foundTown = mapViewModel.foundTown.collectAsState(initial = false)
    val cityName = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                MapBar(navController, mapViewModel)
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    if (!foundTown.value && mapViewModel.townState.value == null) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.DarkGray,
                                contentColor = Color.White
                            ),
                            onClick = {
                                mapViewModel.startToFoundTown()
                            },
                            modifier = Modifier.align(CenterHorizontally)
                        ) {
                            Text(text = "Основать город")
                        }
                    } else if (mapViewModel.townState.value != null) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.DarkGray,
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    mapViewModel.selectHouseState.value = true
                                }
                            ) {
                                Text(text = "Построить дом")
                            }

                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.DarkGray,
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    mapViewModel.selectFortressState.value = true
                                }
                            ) {
                                Text(text = "Построить замок")
                            }
                        }
                    }

                    DraggableImages(map, mapViewModel)
                }

                if (foundTown.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.Transparent)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = cityName.value,
                                onValueChange = { cityName.value = it },
                                label = { Text(text = "Название города",
                                    color = Color.Gray,
                                    modifier = Modifier
                                        .background(color = Color.White)
                                        .padding(1.dp)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White, // Цвет фона внутри рамки
                                    focusedBorderColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray,
                                    unfocusedBorderColor = Color.Gray // Цвет рамки без фокуса
                                )
                            )

                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.DarkGray,
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    mapViewModel.foundTown(map.value, cityName.value)
                                }
                            ) {
                                Text(text = "Сохранить")
                            }
                        }
                    }
                }

                if (mapViewModel.selectHouseState.value) {
                    SelectBuildingScreen(mapViewModel = mapViewModel)
                }

                if (mapViewModel.selectFortressState.value) {
                    SelectBuildingScreen(mapViewModel = mapViewModel)
                }
            }
        }
    )
}
