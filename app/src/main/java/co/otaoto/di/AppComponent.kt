package co.otaoto.di

import co.otaoto.Application
import co.otaoto.api.ApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ApiModule::class, ActivityBindingModule::class, AndroidInjectionModule::class])
interface AppComponent : AndroidInjector<Application> {
    override fun inject(instance: Application)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}

