package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import kotlin.math.min

@Composable
fun RulerProfileDialog(
    animal: AnimalModel,
    onDismiss: () -> Unit,
    onDepose: () -> Unit
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
                .widthIn(max = min(screenW * 0.92f, 360.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.country_ruler),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                val side = min(
                    240.dp.value,
                    (screenH * 0.38f).value
                ).dp

                Box(
                    modifier = Modifier
                        .size(side)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.Black.copy(alpha = 0.06f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = animal.imagePath,
                        contentDescription = animal.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = animal.kind,
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )

                Text(
                    text = animal.name,
                    style = MaterialTheme.typography.h6
                )

                Spacer(Modifier.height(8.dp))

                val statusLine = when (animal.status) {
                    AnimalStatus.EXPEDITION -> stringResource(R.string.animal_status_expedition)
                    AnimalStatus.FUGITIVE -> stringResource(R.string.animal_status_fugitive)
                    AnimalStatus.SEARCHING -> stringResource(R.string.animal_status_searching)
                    AnimalStatus.GONE -> stringResource(R.string.animal_status_gone)
                    AnimalStatus.PET -> null
                }

                if (statusLine != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color.Black.copy(alpha = 0.08f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = statusLine,
                            style = MaterialTheme.typography.caption,
                            color = Color.DarkGray
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDepose) {
                        Text(
                            text = stringResource(R.string.depose_ruler),
                            color = MaterialTheme.colors.error
                        )
                    }

                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
                    ) {
                        Text(stringResource(R.string.close))
                    }
                }
            }
        }
    }
}