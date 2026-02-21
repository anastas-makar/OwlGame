package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.web.Pouch
import pro.progr.owlgame.data.web.inpouch.InPouch

interface PouchesRepository {

    suspend fun getPouches(): Result<List<Pouch>>

    suspend fun getInPouch(pouchId: String): Result<InPouch>
}

