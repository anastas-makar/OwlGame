package pro.progr.owlgame.presentation.ui.model

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

data class LootHintUi(
    val id: String,
    @StringRes val titleRes: Int,
    @ArrayRes val paragraphsRes: Int
)