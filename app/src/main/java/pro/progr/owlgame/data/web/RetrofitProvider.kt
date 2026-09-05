package pro.progr.owlgame.data.web

import okhttp3.OkHttpClient
import pro.progr.authapi.AuthInterface
import pro.progr.authapi.UnauthorizedInterceptor
import pro.progr.authapi.signingInterceptor
import pro.progr.owlgame.data.db.dao.AppMetaDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private var retrofit : Retrofit? = null

    fun provideRetrofit(
        baseUrl: String,
        auth: AuthInterface,
        appMetaDao: AppMetaDao
    ): Retrofit {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                //.followSslRedirects(false)
                // Игровой заголовок должен быть установлен до вычисления подписи.
                .addInterceptor(GameInstanceIdInterceptor(appMetaDao))
                // затем подписание запроса вместе с игровым заголовком
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
