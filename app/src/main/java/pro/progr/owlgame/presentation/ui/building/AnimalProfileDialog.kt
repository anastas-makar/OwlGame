package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.min
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalStatus
import kotlin.math.min

@Composable
fun AnimalProfileDialog(
    animal: Animal,
    onDismiss: () -> Unit
) {
    val cfg = LocalConfiguration.current
    val screenH = cfg.screenHeightDp.dp
    val screenW = cfg.screenWidthDp.dp

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                // ограничиваем ширину, чтобы диалог выглядел аккуратно на планшетах/ландшафте
                .widthIn(max = min(screenW * 0.92f, 360.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    // если контент не влез — будет скролл вместо клипа
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Заголовок — внутри контента, поэтому никогда не окажется “за картинкой”
                Text(
                    text = "Здесь живёт",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // Картинка: размер адаптивный (чтобы по высоте не резало)
                val side = min(
                    240.dp.value,
                    (screenH * 0.38f).value
                ).dp

                Box(
                    modifier = Modifier
                        .size(side)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.Black.copy(alpha = 0.06f)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = animal.imagePath,
                        contentDescription = null,
                        // FIT — не обрезает, а вписывает целиком
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp) // чтобы не “прилипало” к краям
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = animal.kind,
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
                Text(
                    text = animal.name,
                    style = MaterialTheme.typography.h6
                )

                Spacer(Modifier.height(8.dp))

                // (опционально) статус
                val statusLine = when (animal.status) {
                    AnimalStatus.EXPEDITION -> "Сейчас в экспедиции"
                    AnimalStatus.FUGITIVE -> "Временно в бегах"
                    else -> null
                }
                if (statusLine != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color.Black.copy(alpha = 0.08f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(statusLine, style = MaterialTheme.typography.caption, color = Color.DarkGray)
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Закрыть")
                    }
                }
            }
        }
    }
}