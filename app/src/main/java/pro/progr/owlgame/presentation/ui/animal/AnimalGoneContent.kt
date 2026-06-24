package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel

@Composable
fun AnimalGoneContent(
    animal: AnimalModel,
    backToMain: () -> Unit
) {
    AnimalStatusMessageContent(
        animal = animal,
        text = stringResource(
            R.string.animal_gone_message,
            animal.initialDisplayName
        ),
        buttonText = stringResource(R.string.go_to_main),
        onButtonClick = backToMain
    )
}