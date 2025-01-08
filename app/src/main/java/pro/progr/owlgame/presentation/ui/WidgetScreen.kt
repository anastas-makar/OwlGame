package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.OwlMenuModel

@Composable
fun WidgetScreen(navController: NavHostController) {
    val menuList = listOf(
        OwlMenuModel(
            text = "Карты",
            navigateTo = "owl_navigation",
            imageResource = R.drawable.test1
        ),
        OwlMenuModel(
            text = "Открыть мешочек",
            navigateTo = "owl_navigation/pouch",
            imageResource = R.drawable.pouch
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(menuList) { _, menuItem ->
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
                    .clickable {
                        navController.navigate(menuItem.navigateTo)
                    }
            ) {
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(menuItem.imageResource)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Text(text = menuItem.text)

                }


            }
        }
    }
}