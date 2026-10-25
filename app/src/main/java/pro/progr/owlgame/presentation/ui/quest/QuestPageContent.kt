package pro.progr.owlgame.presentation.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.QuestOptionModel
import pro.progr.owlgame.domain.model.QuestPageModel

@Composable
fun QuestPageContent(
    page: QuestPageModel,
    isCompleting: Boolean,
    onOptionClick: (QuestOptionModel) -> Unit,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val imageShape = RoundedCornerShape(16.dp)
                val imageHeight = maxWidth.coerceAtMost(320.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .clip(imageShape)
                        .background(Color.Black.copy(alpha = 0.06f))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(page.imageUrl)
                            .build(),
                        contentDescription = page.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        page.name?.let { title ->
            item {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Text(
                text = page.description,
                style = MaterialTheme.typography.body1
            )
        }

        if (page.endingId != null) {
            item {
                Button(
                    onClick = onCompleteClick,
                    enabled = !isCompleting,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isCompleting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colors.onPrimary
                        )
                    } else {
                        Text(stringResource(R.string.complete_quest))
                    }
                }
            }
        } else {
            items(page.options) { option ->
                Button(
                    onClick = { onOptionClick(option) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(option.description)
                }
            }
        }
    }
}
