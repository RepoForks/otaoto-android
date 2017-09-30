package co.otaoto.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

abstract class BaseViewModel<in V : BaseViewModel.View> : ViewModel() {
    interface View

    abstract class Factory<out VM : BaseViewModel<*>> : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>?): T = create() as T

        protected abstract fun create(): VM
    }

    private val _loadingDialogVisible = MutableLiveData<Boolean>()
    val loadingDialogVisible: LiveData<Boolean> = _loadingDialogVisible

    open fun init(view: V) {}

    protected fun showLoadingDialog() {
        _loadingDialogVisible.value = true
    }

    protected fun hideLoadingDialog() {
        _loadingDialogVisible.value = false
    }
}
