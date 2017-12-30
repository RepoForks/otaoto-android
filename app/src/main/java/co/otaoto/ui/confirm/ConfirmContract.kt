package co.otaoto.ui.confirm

import android.arch.lifecycle.LiveData
import co.otaoto.ui.base.BaseContract

interface ConfirmContract {
    companion object {
        internal const val PARAM_SECRET = "secret"
        internal const val PARAM_SLUG = "slug"
        internal const val PARAM_KEY = "key"
    }

    interface View : BaseContract.View

    interface ViewModel : BaseContract.ViewModel {
        fun setSecretVisible(visible: Boolean)
        fun clickLink()
        fun clickCreateAnother()
        val url: LiveData<String>
        val secretValue: LiveData<String>
        val secretVisible: LiveData<Boolean>
        val shareTrigger: LiveData<Unit>
        val moveToCreateTrigger: LiveData<Unit>
    }
}

