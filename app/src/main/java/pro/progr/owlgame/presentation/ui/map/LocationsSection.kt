package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.LocationWithScenesModel

fun LazyListScope.locationsSection(
    locations: List<LocationWithScenesModel>,
    onLocationClick: (LocationWithScenesModel) -> Unit
) {
    if (locations.isEmpty()) return

    item {
        Text(
            text = "Достопримечательности",
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
            fontWeight = FontWeight.Bold
        )
    }

    val rows = locations
        .sortedWith(compareBy({ it.x }, { it.id }))
        .chunked(3)

    items(rows) { row ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            row.forEach { location ->
                LocationCard(
                    location = location,
                    modifier = Modifier.weight(1f),
                    onClick = { onLocationClick(location) }
                )
            }

            repeat(3 - row.size) {
                Spacer(Modifier.weight(1f))
            }
        }
    }
}