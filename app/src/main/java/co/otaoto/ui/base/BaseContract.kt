package co.otaoto.ui.base

import android.arch.lifecycle.LiveData

interface BaseContract {
    interface View {
        fun <T> LiveData<T>.observe(observer: (T?) -> Unit)
        fun <T> LiveData<T>.observeNonNull(observer: (T) -> Unit)
    }

    interface ViewModel {
        val loadingDialogVisible: LiveData<Boolean>
        suspend fun withLoadingDialog(block: suspend () -> Unit)
    }
}
