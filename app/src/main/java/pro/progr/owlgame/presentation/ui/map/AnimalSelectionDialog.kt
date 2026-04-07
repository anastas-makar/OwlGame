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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import pro.progr.owlgame.data.db.entity.Animal

@Composable
fun AnimalSelectionDialog(
    animals: List<Animal>,
    selectedAnimalId: String?,
    onDismiss: () -> Unit,
    onAnimalClick: (Animal) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Выберите животное",
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn {
                    items(animals.size) { ind ->
                        val animal = animals[ind]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAnimalClick(animal) }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = animal.imagePath,
                                contentDescription = animal.name,
                                modifier = Modifier.size(56.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${animal.kind} ${animal.name}",
                                    style = MaterialTheme.typography.subtitle1
                                )

                                if (animal.id == selectedAnimalId) {
                                    Text(
                                        text = "Выбрано",
                                        style = MaterialTheme.typography.caption
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}