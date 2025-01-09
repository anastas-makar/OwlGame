package pro.progr.owlgame.presentation.ui.model;

import pro.progr.owlgame.data.web.inpouch.BuildingType
import pro.progr.owlgame.data.web.inpouch.InPouch

class InPouchDescription constructor(val inPouch : InPouch) {
    private val content : ArrayList<String> = ArrayList()

    init {
        addDiamonds()
        addMaps()
        addHouses()
        addFortresses()
    }

    private fun addDiamonds() {
        if (inPouch.diamonds.isNotEmpty()) {
            content.add("")
        }
    }

    private fun addMaps() {
        if (inPouch.maps.isNotEmpty()) {
            content.add("")
        }
    }

    private fun addHouses() {
        if (inPouch.buildings.isNotEmpty()
            && inPouch.buildings.any { it.type == BuildingType.HOUSE }) {
            content.add("")
        }
    }

    private fun addFortresses() {
        if (inPouch.buildings.isNotEmpty()
            && inPouch.buildings.any { it.type == BuildingType.FORTRESS }) {
            content.add("")
        }
    }

    fun get() : String {
        return content.joinToString(", ", "В мешочке ")
    }

}
