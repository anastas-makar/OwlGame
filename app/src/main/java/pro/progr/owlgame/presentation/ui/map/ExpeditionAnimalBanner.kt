package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel

@Composable
fun ExpeditionAnimalBanner(
    selectedAnimal: AnimalModel?,
    hasAnyPets: Boolean,
    canChooseAnotherPet: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 10.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .then(
                if (canChooseAnotherPet) Modifier.clickable(onClick = onClick)
                else Modifier
            ),
        elevation = 4.dp,
        backgroundColor = Color(0xFFF5F5F5)
    ) {
        when {
            !hasAnyPets -> {
                Text(
                    text = stringResource(R.string.no_animals_for_expedition),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.body1
                )
            }

            selectedAnimal == null -> {
                Text(
                    text = stringResource(R.string.expedition_animal_not_selected),
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.body1
                )
            }

            else -> {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = selectedAnimal.imagePath,
                        contentDescription = selectedAnimal.name,
                        modifier = Modifier.size(72.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = stringResource(
                                R.string.animal_prepares_for_expedition,
                                selectedAnimal.kind,
                                selectedAnimal.name.orEmpty()
                            ),
                            style = MaterialTheme.typography.subtitle1
                        )

                        if (canChooseAnotherPet) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = stringResource(R.string.choose_another_animal_hint),
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }
        }
    }
}
