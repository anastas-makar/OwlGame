package pro.progr.owlgame.presentation.ui

class PouchesList(private val pouches: List<String>) : ArrayList<String>(pouches) {
    override val size: Int
        get() = Integer.MAX_VALUE
    override fun get(index: Int): String {
        return super.get(index % pouches.size)
    }
}
