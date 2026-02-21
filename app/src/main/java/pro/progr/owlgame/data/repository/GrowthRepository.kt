package pro.progr.owlgame.data.repository

interface GrowthRepository {

    fun setGrowthUpdate(updateTime: Long)

    fun getGrowthState() : GrowthState
}

