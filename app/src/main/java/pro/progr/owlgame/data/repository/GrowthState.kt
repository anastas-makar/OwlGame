package pro.progr.owlgame.data.repository

sealed interface GrowthState {

    data class Growing(val delta: Float,
                  val updateTime: Long) : GrowthState

    data object Suspended : GrowthState

    data class NeedsStart(val updateTime: Long) : GrowthState
}