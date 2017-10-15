package co.otaoto.ui.confirm

import android.arch.lifecycle.MutableLiveData
import co.otaoto.ui.base.BaseViewModel
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_KEY
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SECRET
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SLUG
import javax.inject.Inject
import javax.inject.Named

class ConfirmViewModel(private val secret: String, slug: String, key: String) : BaseViewModel<ConfirmContract.View>(), ConfirmContract.ViewModel {
    class Factory @Inject constructor(
            @Named(PARAM_SECRET) private val secret: String,
            @Named(PARAM_SLUG) private val slug: String,
            @Named(PARAM_KEY) private val key: String
    ) : BaseViewModel.Factory<ConfirmViewModel>() {
        override fun create(): ConfirmViewModel = ConfirmViewModel(secret, slug, key)
    }

    private val url: String = "https://otaoto.co/gate/$slug/$key"

    private val secretVisible = MutableLiveData<Boolean>()
    private val shareTrigger = MutableLiveData<Unit>()
    private val moveToCreateTrigger = MutableLiveData<Unit>()

    override fun init(view: ConfirmContract.View) {
        super.init(view)
        view.setLinkUrl(url)
        view.observe(secretVisible) { visible: Boolean? ->
            if (visible == true) {
                setSecretText(secret)
                showSecret()
            } else {
                hideSecret()
                setSecretText("")
            }
        }
        view.observe(shareTrigger) {
            shareUrl(url)
        }
        view.observe(moveToCreateTrigger) {
            moveToCreateScreen()
        }
    }

    override fun setSecretVisible(visible: Boolean) {
        secretVisible.value = visible
    }

    override fun clickLink() {
        shareTrigger.value = Unit
    }

    override fun clickCreateAnother() {
        moveToCreateTrigger.value = Unit
    }
}
