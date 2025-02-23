package pro.progr.owlgame.presentation.ui.model;

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import pro.progr.owlgame.R
import pro.progr.owlgame.data.web.inpouch.BuildingType
import pro.progr.owlgame.data.web.inpouch.InPouch

class InPouchDescription constructor(val inPouch : InPouch) {
    private val content : ArrayList<String> = ArrayList()

    @Composable
    fun compile() : String {
        content.clear()
        addDiamonds()
        addMaps()
        addHouses()
        addFortresses()

        return "В мешочке " +
                if (content.isEmpty()) "ничего нет" else content.joinToString(", ")
    }

    @Composable
    private fun addDiamonds() {
        inPouch.diamonds?.let {
            content.add(
                "${it.amount} " +
                LocalContext.current.resources
                    .getQuantityString(R.plurals.word_diamond, it.amount)
            )
        }
    }

    @Composable
    private fun addMaps() {
        if (inPouch.maps.isNotEmpty()) {
            content.add(
                "${inPouch.maps.size} " +
                LocalContext.current.resources
                    .getQuantityString(R.plurals.word_map, inPouch.maps.size)
            )
        }
    }

    @Composable
    private fun addHouses() {
        if (inPouch.buildings.isEmpty()) {
            return
        }

        val housesAmount = inPouch.buildings.filter { it.type == BuildingType.HOUSE }.size

        if (housesAmount > 0) {

            content.add(
                "$housesAmount " +
                LocalContext.current.resources
                .getQuantityString(R.plurals.word_house, housesAmount))
        }
    }

    @Composable
    private fun addFortresses() {
        if (inPouch.buildings.isEmpty()) {
            return
        }

        val fortressesAmount = inPouch.buildings.filter { it.type == BuildingType.FORTRESS }.size

        if (fortressesAmount > 0) {
            content.add(
                "$fortressesAmount " +
                LocalContext.current.resources
                .getQuantityString(R.plurals.word_house, fortressesAmount))
        }
    }

}
