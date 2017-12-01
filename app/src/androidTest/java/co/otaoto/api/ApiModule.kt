package co.otaoto.api

import dagger.Module
import dagger.Provides

@Module
object ApiModule {
    @Provides
    @JvmStatic
    fun providesApi(): OtaotoApi = TestApi
}
