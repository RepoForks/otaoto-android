package co.otaoto.ui.confirm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import co.otaoto.ui.base.BaseViewModel
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_KEY
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SECRET
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SLUG
import javax.inject.Inject
import javax.inject.Named

class ConfirmViewModel(secret: String, slug: String, key: String) : BaseViewModel(), ConfirmContract.ViewModel {
    class Factory @Inject constructor(
            @Named(PARAM_SECRET) private val secret: String,
            @Named(PARAM_SLUG) private val slug: String,
            @Named(PARAM_KEY) private val key: String
    ) : BaseViewModel.Factory<ConfirmViewModel>() {
        override fun create(): ConfirmViewModel = ConfirmViewModel(secret, slug, key)
    }

    private val _secretValue = MutableLiveData<String>()
    override val secretValue: LiveData<String>
        get() = _secretValue
    private val _url = MutableLiveData<String>()
    override val url: LiveData<String>
        get() = _url
    private val _secretVisible = MutableLiveData<Boolean>()
    override val secretVisible: LiveData<Boolean>
        get() = _secretVisible
    private val _shareTrigger = MutableLiveData<Unit>()
    override val shareTrigger: LiveData<Unit>
        get() = _shareTrigger
    private val _moveToCreateTrigger = MutableLiveData<Unit>()
    override val moveToCreateTrigger: LiveData<Unit>
        get() = _moveToCreateTrigger

    init {
        _url.value = "https://otaoto.co/gate/$slug/$key"
        _secretValue.value = secret
        _secretVisible.value = false
    }

    override fun setSecretVisible(visible: Boolean) {
        _secretVisible.value = visible
    }

    override fun clickLink() {
        _shareTrigger.value = Unit
    }

    override fun clickCreateAnother() {
        _moveToCreateTrigger.value = Unit
    }
}
