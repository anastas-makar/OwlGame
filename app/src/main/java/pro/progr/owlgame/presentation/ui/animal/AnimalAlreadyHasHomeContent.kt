package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel

@Composable
fun AnimalAlreadyHasHomeContent(
    animal: AnimalModel,
    backToMain: () -> Unit
) {
    val kind = animal.kind.replaceFirstChar { it.uppercase() }
    AnimalStatusMessageContent(
        animal = animal,
        text = stringResource(R.string.animal_already_has_home, kind, animal.name.orEmpty()),
        buttonText = stringResource(R.string.go_to_main_screen),
        onButtonClick = backToMain
    )
}
