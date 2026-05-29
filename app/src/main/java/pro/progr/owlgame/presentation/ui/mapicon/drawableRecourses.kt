package pro.progr.owlgame.presentation.ui.mapicon

import androidx.annotation.DrawableRes
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.model.LocationType
import pro.progr.owlgame.domain.model.BuildingType
import pro.progr.owlgame.domain.model.ItemType

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
    ItemType.MUSHROOM -> R.drawable.ic_mushroom
}

@DrawableRes
fun locationIconRes(type: LocationType): Int = when (type) {
    LocationType.WATER_ANOMALY -> R.drawable.ic_water_anomaly
    LocationType.FOUNTAIN -> R.drawable.ic_fountain
    LocationType.WATERFALL -> R.drawable.ic_waterfall
    LocationType.LANDMARK -> R.drawable.ic_landmark
    LocationType.MONUMENT -> R.drawable.ic_monument
    LocationType.CAVE -> R.drawable.ic_cave
    LocationType.PARK -> R.drawable.ic_park
    LocationType.PAVILION -> R.drawable.ic_pavilion
    LocationType.RESORT -> R.drawable.ic_resort
    LocationType.RUINS -> R.drawable.ic_ruins
}

@DrawableRes
fun plantIconRes(): Int = R.drawable.ic_plant

@DrawableRes
fun enemyIconRes(): Int = R.drawable.ic_enemy