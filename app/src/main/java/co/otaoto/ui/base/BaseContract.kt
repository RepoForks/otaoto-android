package co.otaoto.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

interface BaseView {
    fun <T> observe(liveData: LiveData<T>, observer: Observer<T>)
    fun showLoadingDialog()
    fun hideLoadingDialog()
}

interface BasePresenter<in V : BaseView> {
    fun init(view: V)
}
