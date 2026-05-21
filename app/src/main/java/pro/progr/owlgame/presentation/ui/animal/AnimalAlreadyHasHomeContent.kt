package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.runtime.Composable
import pro.progr.owlgame.domain.model.AnimalModel

@Composable
fun AnimalAlreadyHasHomeContent(
    animal: AnimalModel,
    backToMain: () -> Unit
) {
    AnimalStatusMessageContent(
        animal = animal,
        text = "${animal.kind.replaceFirstChar { it.uppercase() }} ${animal.name} уже живёт в своём доме",
        buttonText = "На главную",
        onButtonClick = backToMain
    )
}