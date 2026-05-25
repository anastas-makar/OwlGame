package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.StreetWithBuildingsModel
import kotlin.math.min

@Composable
fun MoveBuildingDialog(
    building: BuildingWithAnimalModel,
    streets: List<StreetWithBuildingsModel>,
    onDismiss: () -> Unit,
    onMove: (String?) -> Unit
) {
    val cfg = LocalConfiguration.current
    val screenH = cfg.screenHeightDp.dp
    val screenW = cfg.screenWidthDp.dp

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                .widthIn(max = min(screenW.value * 0.92f, 380.dp.value).dp)
                .heightIn(max = screenH * 0.88f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Перенести дом",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                val imageHeight = min(
                    220.dp.value,
                    (screenH.value * 0.30f)
                ).dp

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.Black.copy(alpha = 0.06f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(building.imageUrl)
                            .build(),
                        contentDescription = building.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = building.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                building.animal?.let {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Живёт ${it.kind} ${it.name}",
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Куда перенести:",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(6.dp))

                streets
                    .filter { it.id != building.streetId }
                    .forEach { street ->
                        TextButton(
                            onClick = { onMove(street.id) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(street.name)
                        }
                    }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
                    ) {
                        Text("Отмена")
                    }
                }
            }
        }
    }
}