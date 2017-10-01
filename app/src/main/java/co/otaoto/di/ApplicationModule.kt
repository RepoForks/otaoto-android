package co.otaoto.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {
    @Binds
    protected abstract fun bindContext(application: Application): Context
}
