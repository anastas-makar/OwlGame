package pro.progr.owlgame.presentation.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel

@Composable
fun AnimalModel.toLocalizedDisplayName(): String {
    val animalName = name?.takeIf { it.isNotBlank() }

    return if (animalName != null) {
        stringResource(
            R.string.animal_named_display_name,
            animalName,
            kind
        )
    } else {
        initialDisplayName
    }
}