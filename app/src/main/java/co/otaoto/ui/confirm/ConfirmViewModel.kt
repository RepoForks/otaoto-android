package co.otaoto.ui.confirm

import co.otaoto.ui.base.BaseViewModel
import javax.inject.Inject
import javax.inject.Named

class ConfirmViewModel(private val secret: String, slug: String, key: String) : BaseViewModel<ConfirmViewModel.View>() {
    companion object {
        internal const val PARAM_SECRET = "secret"
        internal const val PARAM_SLUG = "slug"
        internal const val PARAM_KEY = "key"
    }

    interface View : BaseViewModel.View {
        fun showSecret()
        fun hideSecret()
        fun setSecretText(text: String)
        fun setLinkUrl(url: String)
        fun shareUrl(url: String)
        fun moveToCreateScreen()
    }

    class Factory @Inject constructor() : BaseViewModel.Factory<ConfirmViewModel>() {
        @Inject
        @field:Named(PARAM_SECRET)
        protected lateinit var secret: String

        @Inject
        @field:Named(PARAM_SLUG)
        protected lateinit var slug: String

        @Inject
        @field:Named(PARAM_KEY)
        protected lateinit var key: String

        override fun create(): ConfirmViewModel = ConfirmViewModel(secret, slug, key)
    }

    private val url: String = "https://otaoto.co/gate/$slug/$key"

    override fun init(view: View) {
        super.init(view)
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
