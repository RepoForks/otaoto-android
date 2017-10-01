package co.otaoto.ui.create

import co.otaoto.ui.base.BasePresenter
import co.otaoto.ui.base.BaseView

interface CreateView : BaseView {
    fun moveToConfirmScreen(secret: String, slug: String, key: String)
    fun showError()
    fun performPasswordVisibleHack()
}

interface CreatePresenter : BasePresenter<CreateView> {
    suspend fun submit(secret: String)
}
