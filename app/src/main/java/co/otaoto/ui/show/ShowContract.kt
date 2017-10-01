package co.otaoto.ui.show

import co.otaoto.ui.base.BasePresenter
import co.otaoto.ui.base.BaseView

interface ShowView : BaseView {
    fun renderGate()
    fun renderShow()
    fun renderGone()
    fun showSecret(secret: String)
    fun moveToCreateScreen()
}

interface ShowPresenter : BasePresenter<ShowView> {
    suspend fun clickReveal();
    fun clickCreateAnother()
}
