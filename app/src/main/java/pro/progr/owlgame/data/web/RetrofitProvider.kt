package pro.progr.owlgame.data.web

import okhttp3.OkHttpClient
import pro.progr.authapi.AuthInterface
import pro.progr.authapi.UnauthorizedInterceptor
import pro.progr.authapi.signingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private var retrofit : Retrofit? = null

    fun provideRetrofit(baseUrl: String, auth: AuthInterface): Retrofit {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .followSslRedirects(false)
                // сначала подписание
                .addInterceptor(signingInterceptor(auth))
                // потом логирование, чтобы лог видел уже подписанные заголовки
                //.addInterceptor(loggingInterceptor(isDebug))
                .addInterceptor(UnauthorizedInterceptor(auth))
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