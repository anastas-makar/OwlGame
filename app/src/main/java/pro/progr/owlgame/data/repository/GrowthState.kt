package pro.progr.owlgame.data.repository

sealed interface GrowthState {

    class Growing(val delta: Float,
                  val updateTime: Long) : GrowthState

    class Suspended : GrowthState

    class NotStarted(val updateTime: Long) : GrowthState
}