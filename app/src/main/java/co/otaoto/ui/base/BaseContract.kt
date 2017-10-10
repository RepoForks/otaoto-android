package co.otaoto.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

interface BaseContract {
    interface View {
        fun <T> observe(liveData: LiveData<T>, observer: Observer<T>)
        fun showLoadingDialog()
        fun hideLoadingDialog()
    }

    interface ViewModel<in V : View> {
        fun init(view: V)
    }
}
