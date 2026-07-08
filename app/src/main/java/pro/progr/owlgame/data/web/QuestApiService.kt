package pro.progr.owlgame.data.web

import pro.progr.owlgame.data.web.quest.CompleteQuestRequest
import pro.progr.owlgame.data.web.quest.CompleteQuestResponse
import pro.progr.owlgame.data.web.quest.QuestApiModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuestApiService {

    @GET("quests/{questId}")
    suspend fun getQuest(
        @Path("questId") questId: String
    ): Response<QuestApiModel>

    @POST("quests/{questId}/complete")
    suspend fun completeQuest(
        @Path("questId") questId: String,
        @Body request: CompleteQuestRequest
    ): Response<CompleteQuestResponse>
}