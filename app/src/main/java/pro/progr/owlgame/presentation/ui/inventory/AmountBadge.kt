package pro.progr.owlgame.presentation.ui.inventory

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R

@Composable
fun AmountBadge(
    amount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(6.dp),
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 2.dp,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(999.dp)
    ) {
        Text(
            text = stringResource(R.string.inventory_item_amount, amount),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}