package pro.progr.owlgame.presentation.ui.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.InventoryCategoryType
import pro.progr.owlgame.presentation.ui.model.InventoryCategoryUi

@Composable
fun InventoryContent(
    categories: List<InventoryCategoryUi>
) {
    if (categories.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.inventory_empty))
        }
        return
    }

    var selectedCategoryType by rememberSaveable {
        mutableStateOf(InventoryCategoryType.BUILDINGS)
    }

    val selectedIndex = categories
        .indexOfFirst { it.type == selectedCategoryType }
        .takeIf { it >= 0 }
        ?: 0

    val selectedCategory = categories[selectedIndex]

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedIndex,
            edgePadding = 12.dp,
            backgroundColor = Color.Transparent
        ) {
            categories.forEachIndexed { index, category ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = {
                        selectedCategoryType = category.type
                    },
                    text = {
                        Text(text = stringResource(category.titleRes))
                    }
                )
            }
        }

        InventoryCategoryHeader(category = selectedCategory)

        if (selectedCategory.items.isEmpty()) {
            InventoryEmptyCategory()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 116.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = selectedCategory.items,
                    key = { it.id }
                ) { item ->
                    InventoryItemCard(item = item)
                }
            }
        }
    }
}