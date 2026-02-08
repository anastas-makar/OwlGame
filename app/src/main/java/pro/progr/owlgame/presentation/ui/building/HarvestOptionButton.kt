package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun HarvestOptionButton(
    imageUrl: String?,
    title: String,
    subtitle: String,
    amountText: String,
    onClick: () -> Unit,
    enabled: Boolean,
    outlined: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)

    val buttonModifier = modifier
        .fillMaxWidth()
        .heightIn(min = 56.dp)

    val content: @Composable RowScope.() -> Unit = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Превью
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Black.copy(alpha = 0.06f)),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // если supply ещё не пришёл
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            // Тексты
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.body1)
                Text(subtitle, style = MaterialTheme.typography.caption, color = Color.Gray)
            }

            // +N справа
            Text(
                text = amountText,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    }

    if (outlined) {
        OutlinedButton(
            onClick = onClick,
            enabled = enabled,
            modifier = buttonModifier,
            shape = shape,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
            content = content
        )
    } else {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = buttonModifier,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White,
                disabledBackgroundColor = Color.DarkGray.copy(alpha = 0.35f),
                disabledContentColor = Color.White.copy(alpha = 0.6f)
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
            content = content
        )
    }
}
