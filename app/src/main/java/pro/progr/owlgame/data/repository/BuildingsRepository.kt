package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingsDao
import pro.progr.owlgame.presentation.ui.model.BuildingModel
import javax.inject.Inject

class BuildingsRepository @Inject constructor(private val buildingsDao : BuildingsDao) {
   fun getAvailableHouses() : Flow<List<Building>> {
       //todo:
       return flowOf(listOf(
           Building(1,
               "какое-то",
               "test1"),
           Building(2,
               "какое-то",
               "test2"),
           Building(3,
               "какое-то",
               "test3"
           )
       ))
   }

   fun getBuildingsOnMap() : Flow<List<Building>> {
       //todo:
       return flowOf(listOf(
           Building(1,
               "какое-то",
               "test1"),
           Building(2,
               "какое-то",
               "test2"),
           Building(3,
               "какое-то",
               "test3"
           )
       ))
   }

   fun getAvailableFortresses() : Flow<Building> {
       TODO()
   }
}