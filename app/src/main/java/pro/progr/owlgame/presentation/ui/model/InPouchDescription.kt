package pro.progr.owlgame.presentation.ui.model

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.BuildingType
import pro.progr.owlgame.domain.model.GardenType
import pro.progr.owlgame.domain.model.PouchItemsModel

class InPouchDescription(
    private val inPouch: PouchItemsModel
) {
    @Composable
    fun compile(): String {
        val resources = LocalContext.current.resources

        val content = buildList {
            addDiamonds(resources)
            addMaps(resources)
            addHouses(resources)
            addFortresses(resources)
            addLocations(resources)
            addFurniture(resources)
            addRecipes(resources)
            addPlants(resources)
            addKitchenGardenItems(resources)
            addGardenItems(resources)
            addPoolItems(resources)
        }

        return if (content.isEmpty()) {
            resources.getString(R.string.in_pouch_empty)
        } else {
            resources.getString(
                R.string.in_pouch_contains,
                content.joinToString(", ")
            )
        }
    }

    private fun MutableList<String>.addCount(
        resources: Resources,
        count: Int,
        pluralResId: Int
    ) {
        if (count <= 0) return

        add(
            resources.getQuantityString(
                pluralResId,
                count,
                count
            )
        )
    }

    private fun MutableList<String>.addDiamonds(resources: Resources) {
        val amount = inPouch.diamonds?.amount ?: return

        addCount(
            resources = resources,
            count = amount,
            pluralResId = R.plurals.word_diamond
        )
    }

    private fun MutableList<String>.addMaps(resources: Resources) {
        addCount(
            resources = resources,
            count = inPouch.maps.size,
            pluralResId = R.plurals.word_map
        )
    }

    private fun MutableList<String>.addHouses(resources: Resources) {
        val count = inPouch.buildings.count { it.type == BuildingType.HOUSE }

        addCount(
            resources = resources,
            count = count,
            pluralResId = R.plurals.word_house
        )
    }

    private fun MutableList<String>.addFortresses(resources: Resources) {
        val count = inPouch.buildings.count { it.type == BuildingType.FORTRESS }

        addCount(
            resources = resources,
            count = count,
            pluralResId = R.plurals.word_fortress
        )
    }

    private fun MutableList<String>.addLocations(resources: Resources) {
        addCount(
            resources = resources,
            count = inPouch.locations.size,
            pluralResId = R.plurals.word_location
        )
    }

    private fun MutableList<String>.addFurniture(resources: Resources) {
        addCount(
            resources = resources,
            count = inPouch.furniture.size,
            pluralResId = R.plurals.word_furniture
        )
    }

    private fun MutableList<String>.addRecipes(resources: Resources) {
        addCount(
            resources = resources,
            count = inPouch.recipes.size,
            pluralResId = R.plurals.word_recipe
        )
    }

    private fun MutableList<String>.addPlants(resources: Resources) {
        addCount(
            resources = resources,
            count = inPouch.plants.size,
            pluralResId = R.plurals.word_plant
        )
    }

    private fun MutableList<String>.addKitchenGardenItems(resources: Resources) {
        val count = inPouch.gardenItems.count {
            it.gardenType == GardenType.KITCHEN_GARDEN
        }

        addCount(
            resources = resources,
            count = count,
            pluralResId = R.plurals.word_kitchen_garden_items
        )
    }

    private fun MutableList<String>.addGardenItems(resources: Resources) {
        val count = inPouch.gardenItems.count {
            it.gardenType == GardenType.GARDEN
        }

        addCount(
            resources = resources,
            count = count,
            pluralResId = R.plurals.word_garden_items
        )
    }

    private fun MutableList<String>.addPoolItems(resources: Resources) {
        val count = inPouch.gardenItems.count {
            it.gardenType == GardenType.POOL
        }

        addCount(
            resources = resources,
            count = count,
            pluralResId = R.plurals.word_pool_items
        )
    }
}