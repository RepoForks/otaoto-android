package co.otaoto.ui.show

import co.otaoto.ui.base.BaseContract

interface ShowContract {
    interface View : BaseContract.View {
        fun renderGate()
        fun renderShow()
        fun renderGone()
        fun showSecret(secret: String)
        fun moveToCreateScreen()
    }

    interface ViewModel : BaseContract.ViewModel<View> {
        suspend fun clickReveal()
        fun clickCreateAnother()
    }
}
