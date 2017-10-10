package co.otaoto.ui.create

import co.otaoto.ui.base.BaseContract

interface CreateContract {
    interface View : BaseContract.View {
        fun moveToConfirmScreen(secret: String, slug: String, key: String)
        fun showError()
        fun performPasswordVisibleHack()
    }

    interface ViewModel : BaseContract.ViewModel<View> {
        suspend fun submit(secret: String)
    }
}
