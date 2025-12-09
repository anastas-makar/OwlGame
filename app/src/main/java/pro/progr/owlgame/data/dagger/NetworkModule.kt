package pro.progr.owlgame.data.dagger

import dagger.Module
import dagger.Provides
import pro.progr.authapi.AuthInterface
import pro.progr.owlgame.BuildConfig
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapWithDataDao
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.AnimalApiService
import pro.progr.owlgame.data.web.MapApiService
import pro.progr.owlgame.data.web.RetrofitProvider
import retrofit2.Retrofit
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
    fun provideRetrofit(@Named("baseUrl") baseUrl: String,
                        auth: AuthInterface): Retrofit {
        return RetrofitProvider.provideRetrofit(baseUrl, auth)
    }

    @Provides
    @Singleton
    fun provideMapApiService(retrofit: Retrofit): MapApiService {
        return retrofit.create(MapApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimalApiService(retrofit: Retrofit): AnimalApiService {
        return retrofit.create(AnimalApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMapRepository(apiService: MapApiService,
                             mapDao: MapDao,
                             mapsWithDataDao: MapWithDataDao,
                             @Named("apiKey") apiKey: String): MapsRepository {
        return MapsRepository(apiService, mapDao, mapsWithDataDao, apiKey)
    }
}
