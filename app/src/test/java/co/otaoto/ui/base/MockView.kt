package co.otaoto.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

abstract class MockView : BaseContract.View {
    override fun <T> observe(liveData: LiveData<T>, observer: Observer<T>) = liveData.observeForever(observer)
}
