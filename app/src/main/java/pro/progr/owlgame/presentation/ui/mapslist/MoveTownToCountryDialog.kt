package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.CountryModel
import pro.progr.owlgame.domain.model.MapModel

@Composable
fun MoveTownToCountryDialog(
    town: MapModel,
    countries: List<CountryModel>,
    onDismiss: () -> Unit,
    onMove: (countryId: String?) -> Unit
) {
    var selectedCountryId by rememberSaveable(town.id) {
        mutableStateOf(town.countryId)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.move_town_to_country))
        },
        text = {
            LazyColumn {
                item {
                    CountryRadioItem(
                        name = stringResource(R.string.free_towns),
                        selected = selectedCountryId == null,
                        onClick = { selectedCountryId = null }
                    )
                }

                items(
                    items = countries,
                    key = { it.id }
                ) { country ->
                    CountryRadioItem(
                        name = country.name,
                        selected = selectedCountryId == country.id,
                        onClick = { selectedCountryId = country.id }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = selectedCountryId != town.countryId,
                onClick = { onMove(selectedCountryId) }
            ) {
                Text(stringResource(R.string.move))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
private fun CountryRadioItem(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )

        Text(
            text = name,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}