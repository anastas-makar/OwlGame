package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import pro.progr.owlgame.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import pro.progr.owlgame.presentation.ui.model.ExpeditionPreparationUiState

@Composable
fun ExpeditionPreparationDialog(
    state: ExpeditionPreparationUiState,
    diamondsAvailable: Int,
    onDismiss: () -> Unit,
    onIncreaseSupply: (String) -> Unit,
    onDecreaseSupply: (String) -> Unit,
    onExtraHealChange: (String) -> Unit,
    onExtraDamageChange: (String) -> Unit,
    onStartClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Подготовка к экспедиции",
                    style = MaterialTheme.typography.h6
                )

                Spacer(Modifier.height(12.dp))

                state.items.forEach { item ->
                    SupplyCatalogRow(
                        item = item,
                        onMinus = { onDecreaseSupply(item.supply.id) },
                        onPlus = { onIncreaseSupply(item.supply.id) }
                    )
                    Spacer(Modifier.height(8.dp))
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                DiamondManualRow(
                    title = "Чешуйки",
                    subtitle = "1 чешуйка = 1 heal, цена 1 бриллиант",
                    imageResource = R.drawable.ic_scale,
                    value = state.extraHealText,
                    onValueChange = onExtraHealChange
                )

                Spacer(Modifier.height(8.dp))

                DiamondManualRow(
                    title = "Бомбочки",
                    subtitle = "1 бомбочка = 1 damage, цена 1 бриллиант",
                    imageResource = R.drawable.ic_bomb,
                    value = state.extraDamageText,
                    onValueChange = onExtraDamageChange
                )

                Spacer(Modifier.height(16.dp))

                Text("Итого heal: ${state.totalHeal}")
                Text("Итого damage: ${state.totalDamage}")
                Text("Цена: ${state.diamondsCost} бриллиантов")
                Text("Есть: $diamondsAvailable")

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Отмена")
                    }

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = onStartClick,
                        enabled =
                            !state.isStarting &&
                                    diamondsAvailable >= state.diamondsCost &&
                                    (state.totalHeal > 0 || state.totalDamage > 0)
                    ) {
                        Text("Начать")
                    }
                }
            }
        }
    }
}