package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalStatus

@Composable
fun AnimalStatusLine(status: AnimalStatus) {
    val text = when (status) {
        AnimalStatus.PET -> null
        AnimalStatus.EXPEDITION -> stringResource(R.string.animal_status_expedition)
        AnimalStatus.FUGITIVE -> stringResource(R.string.animal_status_fugitive)
        AnimalStatus.SEARCHING -> stringResource(R.string.animal_status_searching)
        AnimalStatus.GONE -> stringResource(R.string.animal_status_gone)
    }

    if (text != null) {
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = Color.Companion.Gray
        )
    }
}