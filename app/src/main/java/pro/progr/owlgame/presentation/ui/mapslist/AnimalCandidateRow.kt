package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus

@Composable
fun AnimalCandidateRow(
    animal: AnimalModel,
    selected: Boolean,
    enabled: Boolean,
    availableText: String,
    onClick: () -> Unit
) {
    val alpha = if (enabled) 1f else 0.45f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            enabled = enabled
        )

        AsyncImage(
            model = animal.imagePath,
            contentDescription = animal.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 6.dp)
                .size(52.dp)
                .clip(RoundedCornerShape(10.dp))
                .alpha(alpha)
        )

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${animal.kind} ${animal.name}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = if (enabled) Color.Unspecified else Color.Gray
            )

            val statusText = when (animal.status) {
                AnimalStatus.PET -> availableText
                AnimalStatus.EXPEDITION -> stringResource(R.string.cannot_appoint_expedition)
                AnimalStatus.FUGITIVE -> stringResource(R.string.cannot_appoint_fugitive)
                AnimalStatus.SEARCHING -> stringResource(R.string.cannot_appoint_unavailable)
                AnimalStatus.GONE -> stringResource(R.string.cannot_appoint_unavailable)
            }

            Text(
                text = statusText,
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}