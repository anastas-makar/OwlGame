package pro.progr.owlgame.presentation.ui.draggableimages

import androidx.annotation.DrawableRes
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.BuildingType
import pro.progr.owlgame.data.db.ItemType

@DrawableRes
fun buildingIconRes(type: BuildingType): Int = when (type) {
    BuildingType.HOUSE -> R.drawable.map_icon_house
    BuildingType.FORTRESS -> R.drawable.map_icon_fortress
}

@DrawableRes
fun gardenItemIconRes(type: ItemType): Int = when (type) {
    ItemType.TREE -> R.drawable.ic_tree
    ItemType.FISH -> R.drawable.ic_fish
    ItemType.WATER_PLANT -> R.drawable.ic_water_plant
    ItemType.HIVE -> R.drawable.ic_hive
    ItemType.ANIMAL_HOUSE -> R.drawable.ic_animal_house
    ItemType.NEST -> R.drawable.ic_nest
}

@DrawableRes
fun plantIconRes(): Int = R.drawable.ic_plant