package pro.progr.owlgame.data.repository

sealed interface GrowthState {
    val shouldGrow : Boolean
    val delta : Float
    val updateTime : Long

    class Growing(override val delta: Float,
                  override val updateTime: Long) : GrowthState {
        override val shouldGrow: Boolean = true
    }

    class Suspended : GrowthState {
        override val shouldGrow: Boolean = false
        override val delta: Float
            get() = error("Growth is suspended")
        override val updateTime: Long
            get() = error("Growth is suspended")
    }

    class NotStarted(override val updateTime: Long) : GrowthState {
        override val shouldGrow: Boolean = false
        override val delta: Float = 0f
    }
}