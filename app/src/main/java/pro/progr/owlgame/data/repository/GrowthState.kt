package pro.progr.owlgame.data.repository

sealed interface GrowthState {
    val shouldGrow : Boolean
    val delta : Float

    class Growing(override val delta: Float) : GrowthState {
        override val shouldGrow: Boolean = true
    }

    class Suspended : GrowthState {
        override val shouldGrow: Boolean = false
        override val delta: Float
            get() = error("Growth is suspended")
    }
}