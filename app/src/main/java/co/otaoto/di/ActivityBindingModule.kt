package co.otaoto.di

import co.otaoto.ui.confirm.ConfirmActivity
import co.otaoto.ui.confirm.ConfirmModule
import co.otaoto.ui.create.CreateActivity
import co.otaoto.ui.create.CreateModule
import co.otaoto.ui.show.ShowActivity
import co.otaoto.ui.show.ShowModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [CreateModule::class])
    abstract fun createActivity(): CreateActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ConfirmModule::class])
    abstract fun confirmActivity(): ConfirmActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ShowModule::class])
    abstract fun showActivity(): ShowActivity
}
