package co.otaoto.ui.base

import android.arch.lifecycle.*
import android.support.annotation.CallSuper

abstract class BaseViewModel<V : BaseViewModel.View> : ViewModel() {
    interface View {
        fun <T> observe(liveData: LiveData<T>, observer: Observer<T>)
        fun showLoadingDialog()
        fun hideLoadingDialog()
    }

    abstract class Factory<out VM : BaseViewModel<*>> : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>?): T = create() as T

        protected abstract fun create(): VM
    }

    protected val loadingDialogVisible = MutableLiveData<Boolean>()

    @CallSuper
    open fun init(view: V) {
        view.observe(loadingDialogVisible) { visible: Boolean? ->
            if (visible == true) showLoadingDialog() else hideLoadingDialog()
        }
    }

    protected fun <T> V.observe(liveData: LiveData<T>, observer: V.(T) -> Unit) {
        observe(liveData, Observer { it?.let { observer(it) } })
    }
}
