package pro.progr.owlgame.data.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pro.progr.owlgame.BuildConfig
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapWithDataDao
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.MapApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideRetrofit(@Named("baseUrl") baseUrl: String): Retrofit {
        val client = OkHttpClient.Builder()
            //.followSslRedirects(false)
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMapApiService(retrofit: Retrofit): MapApiService {
        return retrofit.create(MapApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMapRepository(apiService: MapApiService,
                             mapDao: MapDao,
                             mapsWithDataDao: MapWithDataDao,
                             context: Context,
                             @Named("apiKey") apiKey: String,
                             @Named("baseUrl") baseUrl: String): MapsRepository {
        return MapsRepository(apiService, mapDao, mapsWithDataDao, context, apiKey, baseUrl)
    }
}
