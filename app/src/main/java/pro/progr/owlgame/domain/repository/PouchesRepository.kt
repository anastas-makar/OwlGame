package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.PouchItemsModel
import pro.progr.owlgame.domain.model.PouchOfferModel

interface PouchesRepository {

    /**
     * Returns one opening id and all images used by its animation.
     * The caller must retain pouchId and reuse it when retrying an ambiguous open request.
     */
    suspend fun getPouchOffer(): Result<PouchOfferModel>

    /** Repeating this call with the same pouchId returns the same server-side reward. */
    suspend fun getInPouch(pouchId: String): Result<PouchItemsModel>
}
