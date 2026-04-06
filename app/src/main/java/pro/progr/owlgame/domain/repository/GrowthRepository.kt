package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.data.model.GrowthState

interface GrowthRepository {

    fun setGrowthUpdate(updateTime: Long)

    fun getGrowthState() : GrowthState
}

