package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.presentation.mapper.toLocalizedDisplayName

@Composable
fun AnimalSearchingBar(
    backToMain: () -> Unit,
    animalState: State<AnimalModel?>
) {
    val animal = animalState.value

    TopAppBar(
        title = {
            Text(
                text = animal?.let { animalModel ->
                    animalSearchingBarTitle(animalModel)
                }.orEmpty()
            )
        },
        navigationIcon = {
            IconButton(onClick = backToMain) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Composable
private fun animalSearchingBarTitle(
    animal: AnimalModel
): String {
    return if (animal.status == AnimalStatus.SEARCHING) {
        stringResource(
            R.string.animal_searching_title,
            animal.initialDisplayName
        )
    } else {
        stringResource(
            R.string.animal_not_searching_title,
            animal.toLocalizedDisplayName()
        )
    }
}