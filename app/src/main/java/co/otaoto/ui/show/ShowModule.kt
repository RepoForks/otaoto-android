package co.otaoto.ui.show

import co.otaoto.di.ActivityScoped
import dagger.Module
import dagger.Provides

@Module
class ShowModule {

    @Provides
    @ActivityScoped
    fun providePathSegments(activity: ShowActivity): List<String> = activity.intent.data.pathSegments
}
