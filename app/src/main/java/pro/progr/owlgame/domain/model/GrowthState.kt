package pro.progr.owlgame.domain.model

sealed interface GrowthState {

    data class Growing(val delta: Float,
                  val updateTime: Long) : GrowthState

    data object Suspended : GrowthState

    data class NeedsStart(val updateTime: Long) : GrowthState
}