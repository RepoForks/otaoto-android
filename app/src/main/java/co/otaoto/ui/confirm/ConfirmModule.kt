package co.otaoto.ui.confirm

import co.otaoto.di.ActivityScoped
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_KEY
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SECRET
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SLUG
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object ConfirmModule {
    @Provides
    @ActivityScoped
    @Named(PARAM_SECRET)
    @JvmStatic
    fun provideSecret(activity: ConfirmActivity): String = activity.intent.getStringExtra(PARAM_SECRET)

    @Provides
    @ActivityScoped
    @Named(PARAM_SLUG)
    @JvmStatic
    fun provideSlug(activity: ConfirmActivity): String = activity.intent.getStringExtra(PARAM_SLUG)

    @Provides
    @ActivityScoped
    @Named(PARAM_KEY)
    @JvmStatic
    fun provideKey(activity: ConfirmActivity): String = activity.intent.getStringExtra(PARAM_KEY)
}
