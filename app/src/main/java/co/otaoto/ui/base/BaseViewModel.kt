package co.otaoto.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

abstract class BaseViewModel : ViewModel(), BaseContract.ViewModel {
    abstract class Factory<out VM : BaseContract.ViewModel> : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = create() as T

        protected abstract fun create(): VM
    }

    private val _loadingDialogVisible = MutableLiveData<Boolean>()
    override val loadingDialogVisible: LiveData<Boolean>
        get() = _loadingDialogVisible

    override suspend fun withLoadingDialog(block: suspend () -> Unit) {
        _loadingDialogVisible.value = true
        block()
        _loadingDialogVisible.value = false
    }
}
