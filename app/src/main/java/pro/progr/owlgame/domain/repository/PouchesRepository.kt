package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.domain.model.PouchModel

interface PouchesRepository {

    suspend fun getPouches(): Result<List<PouchModel>>

    suspend fun getInPouch(pouchId: String): Result<InPouchModel>
}

