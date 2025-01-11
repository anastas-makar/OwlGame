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
        inPouch.diamonds?.let {
            content.add("${it.amount} " +
                    RuCountable(
                "бриллиантов",
                "бриллиант",
                "бриллианта").getForNum(it.amount)
            )
        }
    }

    private fun addMaps() {
        if (inPouch.maps.isNotEmpty()) {
            content.add("${inPouch.maps.size} " +
                    RuCountable(
                        "карт",
                        "карта",
                        "карты").getForNum(inPouch.maps.size)
            )
        }
    }

    private fun addHouses() {
        if (inPouch.buildings.isEmpty()) {
            return
        }

        val housesAmount = inPouch.buildings.filter { it.type == BuildingType.HOUSE }.size

        if (housesAmount > 0) {
            content.add("$housesAmount " +
                    RuCountable(
                        "домиков",
                        "домик",
                        "домика").getForNum(housesAmount))
        }
    }

    private fun addFortresses() {
        if (inPouch.buildings.isEmpty()) {
            return
        }

        val fortressesAmount = inPouch.buildings.filter { it.type == BuildingType.FORTRESS }.size

        if (fortressesAmount > 0) {
            content.add("$fortressesAmount " +
                    RuCountable(
                        "замков",
                        "замок",
                        "замка").getForNum(fortressesAmount))
        }
    }

    fun get() : String {
        return "В мешочке " +
                if (content.isEmpty()) "ничего нет" else content.joinToString(", ")
    }

}
