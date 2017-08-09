package co.otaoto.ui.base

import android.arch.lifecycle.ViewModel

open class BaseViewModel<V : BaseViewModel.View> : ViewModel() {
    interface View
}
