package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.domain.model.SupplyModel

@Composable
fun SupplyDialog(
    supply: SupplyModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = supply.imageUrl,
                    contentDescription = supply.name,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = supply.name,
                    maxLines = 2
                )
            }
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Text(supply.description)

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Количество: ${supply.amount}",
                    style = MaterialTheme.typography.body2
                )

                supply.effectText()?.let { effect ->
                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = effect,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}