package co.otaoto.ui.confirm

import co.otaoto.ui.base.BaseContract

interface ConfirmContract {
    companion object {
        internal const val PARAM_SECRET = "secret"
        internal const val PARAM_SLUG = "slug"
        internal const val PARAM_KEY = "key"
    }

    interface View : BaseContract.View {
        fun showSecret()
        fun hideSecret()
        fun setSecretText(text: String)
        fun setLinkUrl(url: String)
        fun shareUrl(url: String)
        fun moveToCreateScreen()
    }

    interface ViewModel : BaseContract.ViewModel<View> {
        fun setSecretVisible(visible: Boolean)
        fun clickLink()
        fun clickCreateAnother()
    }
}

