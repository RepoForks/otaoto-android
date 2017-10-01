package co.otaoto.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

abstract class MockView : BaseView {
    override fun <T> observe(liveData: LiveData<T>, observer: Observer<T>) = liveData.observeForever(observer)
}
