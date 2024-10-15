package pro.progr.owlgame.data.dagger

import dagger.Module
import dagger.Provides
import pro.progr.owlgame.BuildConfig
import pro.progr.owlgame.data.repository.MapRepository
import pro.progr.owlgame.data.web.MapApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
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
    fun provideMapRepository(apiService: MapApiService): MapRepository {
        return MapRepository(apiService)
    }
}
