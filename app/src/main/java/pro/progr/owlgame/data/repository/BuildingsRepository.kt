package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingsDao
import javax.inject.Inject

class BuildingsRepository @Inject constructor(private val buildingsDao : BuildingsDao) {
   fun getHouses() : Flow<Building> {
       TODO()
   }

   fun getFortresses() : Flow<Building> {
       TODO()
   }
}