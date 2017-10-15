package co.otaoto.ui.show

import co.otaoto.di.ActivityScoped
import dagger.Module
import dagger.Provides

@Module
object ShowModule {
    @Provides
    @ActivityScoped
    @JvmStatic
    fun providePathSegments(activity: ShowActivity): List<String> = activity.intent.data.pathSegments
}
