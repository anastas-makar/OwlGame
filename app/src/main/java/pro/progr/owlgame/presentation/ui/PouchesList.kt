package pro.progr.owlgame.presentation.ui

import pro.progr.owlgame.data.web.Pouch

class PouchesList(private val pouches : List<Pouch>) : ArrayList<Pouch>(pouches) {
    override val size: Int
        get() = Integer.MAX_VALUE
    override fun get(index: Int): Pouch {
        return super.get(index % pouches.size)
    }
}