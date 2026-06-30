package pro.progr.owlgame.data.web

import pro.progr.owlgame.data.web.merchant.MerchantShopApiModel
import pro.progr.owlgame.data.web.pouchitems.PouchItemsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LootApiService {
    @GET("pouches")
    suspend fun getPouches(): Response<List<String>>

    @GET("inPouch")
    suspend fun getInPouch(
        @Query("pouchId") pouchId: String
    ): Response<PouchItemsDto>

    @GET("loot")
    suspend fun getLoot(
        @Query("expeditionId") expeditionId: String
    ): Response<PouchItemsDto>

    @GET("merchantShop")
    suspend fun getMerchantShop(): Response<MerchantShopApiModel>
}