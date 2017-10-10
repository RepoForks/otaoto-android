package co.otaoto.api

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun providesApi(): Api = MockApi()
}
