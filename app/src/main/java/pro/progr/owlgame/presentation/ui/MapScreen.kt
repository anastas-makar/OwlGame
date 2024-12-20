package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerMapViewModel
import kotlin.math.roundToInt

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
                                    mapViewModel.newHouseState.value = true
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
                                    mapViewModel.newFortressState.value = true
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
            }
        }
    )
}

@Composable
fun DraggableImages(map: State<Map>, mapViewModel: MapViewModel) {
    // Состояния для координат первого и второго изображения
    val houseOffset = remember { mutableStateOf(Offset(50f, 56f)) }
    val fortressOffset = remember { mutableStateOf(Offset(250f, 56f)) }

    Box(modifier = Modifier.fillMaxWidth()) {
        // Основное изображение на фоне
        AsyncImage(
            model = Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = map.value.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
,
                    contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        // Первое изображение (драгаемое)
        if (mapViewModel.newHouseState.value) {
            Image(
                painter = painterResource(R.drawable.map_icon_house),
                contentDescription = "Полупрозрачное изображение",
                modifier = Modifier
                    .offset { IntOffset(houseOffset.value.x.roundToInt(), houseOffset.value.y.roundToInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { _, dragAmount ->
                            houseOffset.value = Offset(
                                x = houseOffset.value.x + dragAmount.x,
                                y = houseOffset.value.y + dragAmount.y
                            )
                        }
                    }
                    .graphicsLayer {
                        shadowElevation = 8f
                        shape = RoundedCornerShape(8.dp)
                        clip = true
                    }
            )
        }

        // Второе изображение (драгаемое)
        if (mapViewModel.newFortressState.value) {
            Image(
                painter = painterResource(R.drawable.map_icon_fortress),
                contentDescription = "Полупрозрачное изображение",
                modifier = Modifier
                    .offset { IntOffset(fortressOffset.value.x.roundToInt(), fortressOffset.value.y.roundToInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { _, dragAmount ->
                            fortressOffset.value = Offset(
                                x = fortressOffset.value.x + dragAmount.x,
                                y = fortressOffset.value.y + dragAmount.y
                            )
                        }
                    }
                    .graphicsLayer {
                        shadowElevation = 8f
                        shape = RoundedCornerShape(8.dp)
                        clip = true
                    }
            )
        }
    }
}
