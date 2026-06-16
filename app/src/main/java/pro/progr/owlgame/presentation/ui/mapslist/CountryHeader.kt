package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.CountryModel

@Composable
fun CountryHeader(
    country: CountryModel,
    modifier: Modifier = Modifier,
    onMoveTownHere: (() -> Unit)? = null,
    onDeleteCountry: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = country.name,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        onMoveTownHere?.let {
            TextButton(onClick = it) {
                Text("Добавить город")
            }
        }

        onDeleteCountry?.let {
            TextButton(onClick = it) {
                Text("Удалить")
            }
        }
    }
}