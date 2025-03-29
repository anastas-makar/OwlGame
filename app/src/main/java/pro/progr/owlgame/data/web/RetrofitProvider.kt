package pro.progr.owlgame.data.web

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private var retrofit : Retrofit? = null

    fun provideRetrofit(baseUrl: String): Retrofit {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                //.followSslRedirects(false)
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }
}