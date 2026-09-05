package pro.progr.owlgame.data.web

import pro.progr.owlgame.data.web.merchant.MerchantShopApiModel
import pro.progr.owlgame.data.web.pouchitems.PouchItemsDto
import pro.progr.owlgame.data.web.quest.QuestLootRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

interface LootApiService {
    @GET("pouches")
    suspend fun getPouchOffer(): Response<PouchOffer>

    @POST("pouches/{pouchId}/open")
    suspend fun getInPouch(
        @Path("pouchId") pouchId: String
    ): Response<PouchItemsDto>

    @POST("expeditions/{expeditionId}/loot")
    suspend fun getLoot(
        @Path("expeditionId") expeditionId: String
    ): Response<PouchItemsDto>

    @POST("quests/{questId}/loot")
    suspend fun getQuestLoot(
        @Path("questId") questId: String,
        @Body request: QuestLootRequest
    ): Response<PouchItemsDto>

    @GET("merchant-shop")
    suspend fun getMerchantShop(): Response<MerchantShopApiModel>
}
