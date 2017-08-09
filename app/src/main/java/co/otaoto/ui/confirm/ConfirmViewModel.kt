package co.otaoto.ui.confirm

import co.otaoto.ui.base.BaseViewModel

class ConfirmViewModel : BaseViewModel<ConfirmViewModel.View>() {
    interface View : BaseViewModel.View {
        fun showSecret()
        fun hideSecret()
        fun setSecretText(text: String)
        fun setLinkUrl(url: String)
        fun shareUrl(url: String)
        fun moveToCreateScreen()
    }

    private lateinit var secret: String
    private lateinit var slug: String
    private lateinit var key: String
    private val url: String get() = "https://otaoto.co/gate/$slug/$key"

    internal fun init(view: View, secret: String, slug: String, key: String) {
        this.secret = secret
        this.slug = slug
        this.key = key
        view.setLinkUrl(url)
    }

    internal fun setSecretVisible(view: View, visible: Boolean) = with(view) {
        if (visible) {
            setSecretText(secret)
            showSecret()
        } else {
            hideSecret()
            setSecretText("")
        }
    }

    internal fun clickLink(view: View) {
        view.shareUrl(url)
    }

    internal fun clickCreateAnother(view: View) {
        view.moveToCreateScreen()
    }
}
