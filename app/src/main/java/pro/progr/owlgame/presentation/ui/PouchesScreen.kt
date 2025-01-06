package pro.progr.owlgame.presentation.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.data.web.Pouch
import pro.progr.owlgame.presentation.viewmodel.PouchesViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerPouchViewModel

@Composable
fun PouchesScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    pouchesViewModel: PouchesViewModel = DaggerPouchViewModel()
) {
    pouchesViewModel.loadPouches()

    val pouches = PouchesList(pouchesViewModel.pouches.value)
    if (pouches.isEmpty()) return // Отображение загрузки, если данные ещё не загружены

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                PouchesBar(backToMain)
            }
        },
        content = { innerPadding ->
            Row(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                repeat(3) { index ->
                    AnimatedPouchesColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        pouches = pouches,
                        direction = if (index % 2 == 0) Direction.DOWN else Direction.UP
                    )
                }
            }
        }
    )
}

enum class Direction { UP, DOWN }

@Composable
fun AnimatedPouchesColumn(
    modifier: Modifier = Modifier,
    pouches: List<Pouch>,
    direction: Direction
) {
    // Состояние списка
    val listState = rememberLazyListState()

    // Запускаем анимацию скролла
    LaunchedEffect(direction, pouches) {
        while (true) {
            // Прокрутка по всем элементам
            for (index in pouches.indices) {
                listState.animateScrollToItem(
                    index = index
                )
            }
        }
    }

    // Колонка с прокруткой
    LazyColumn(
        state = listState,
        modifier = modifier,
        reverseLayout = direction == Direction.DOWN // Обратное направление для DOWN
    ) {
        itemsIndexed(pouches) { _, pouch ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = pouch.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f) // Квадратные изображения
                        .clickable {

                        }
                )
            }
        }
    }
}