package co.otaoto.ui.confirm

import co.otaoto.ui.base.BasePresenter
import co.otaoto.ui.base.BaseView

internal const val PARAM_SECRET = "secret"
internal const val PARAM_SLUG = "slug"
internal const val PARAM_KEY = "key"

interface ConfirmView : BaseView {
    fun showSecret()
    fun hideSecret()
    fun setSecretText(text: String)
    fun setLinkUrl(url: String)
    fun shareUrl(url: String)
    fun moveToCreateScreen()
}

interface ConfirmPresenter : BasePresenter<ConfirmView> {
    fun setSecretVisible(visible: Boolean)
    fun clickLink()
    fun clickCreateAnother()
}
