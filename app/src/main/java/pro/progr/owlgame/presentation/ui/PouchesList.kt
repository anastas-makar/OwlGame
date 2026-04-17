package pro.progr.owlgame.presentation.ui

import pro.progr.owlgame.domain.model.PouchModel

class PouchesList(private val pouches : List<PouchModel>) : ArrayList<PouchModel>(pouches) {
    override val size: Int
        get() = Integer.MAX_VALUE
    override fun get(index: Int): PouchModel {
        return super.get(index % pouches.size)
    }
}