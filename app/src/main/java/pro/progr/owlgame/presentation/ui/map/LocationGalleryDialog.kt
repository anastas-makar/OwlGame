package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import pro.progr.owlgame.domain.model.LocationSceneModel
import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.presentation.ui.building.Thumbnail

@Composable
fun LocationGalleryDialog(
    location: LocationWithScenesModel,
    onDismiss: () -> Unit
) {
    val scenes = remember(location.scenes) {
        location.scenes.sortedBy { it.sceneNumber }
    }

    val items = remember(location, scenes) {
        buildList {
            add(
                LocationSceneModel(
                    id = "${location.id}_main",
                    name = location.name,
                    description = location.description,
                    imageUrl = location.imageUrl,
                    locationId = location.id,
                    sceneNumber = -1
                )
            )
            addAll(scenes)
        }
    }

    var selectedId by rememberSaveable(location.id) {
        mutableStateOf(items.firstOrNull()?.id)
    }

    val selected = remember(items, selectedId) {
        items.firstOrNull { it.id == selectedId } ?: items.firstOrNull()
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                .widthIn(max = 420.dp)
                .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.9f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = location.name,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(items) { item ->
                        val isSelected = item.id == selected?.id

                        Thumbnail(
                            imageUrl = item.imageUrl,
                            isSelected = isSelected,
                            onClick = { selectedId = item.id }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                selected?.let { scene ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false)
                            .verticalScroll(rememberScrollState())
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(scene.imageUrl)
                                .build(),
                            contentDescription = scene.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 260.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Black.copy(alpha = 0.06f))
                                .padding(8.dp)
                        )

                        Spacer(Modifier.height(10.dp))

                        scene.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(4.dp))
                        }

                        Text(
                            text = scene.description,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
                    ) {
                        Text("Закрыть")
                    }
                }
            }
        }
    }
}