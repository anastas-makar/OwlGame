package pro.progr.owlgame.presentation.ui.pouch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.domain.model.PouchModel
import pro.progr.owlgame.presentation.mapper.toLootHints
import pro.progr.owlgame.presentation.mapper.toLootItems
import pro.progr.owlgame.presentation.ui.model.InPouchDescription
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R

@Composable
fun InPouchContent(
    navController: NavHostController,
    inPouchViewModel: InPouchViewModel,
    pouch: PouchModel
) {
    inPouchViewModel.inPouch.value?.let { inPouch ->
        val resources = LocalContext.current.resources
        val configuration = LocalConfiguration.current

        val items = remember(inPouch, configuration) {
            inPouch.toLootItems(resources)
        }

        val hints = remember(items) {
            items.toLootHints()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                InPouchHeader(
                    pouch = pouch,
                    description = InPouchDescription(inPouch).compile()
                )
            }

            if (items.isEmpty()) {
                item {
                    EmptyLootCard()
                }
            } else {
                items(
                    items = items,
                    key = { it.id }
                ) { item ->
                    BigLootItemCard(
                        item = item,
                        onClick = item.route?.let { route ->
                            { navController.navigate(route) }
                        }
                    )
                }
            }

            if (hints.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.in_pouch_hints_title),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(
                    items = hints,
                    key = { it.id }
                ) { hint ->
                    HintCard(hint)
                }
            }
        }
    }
}
