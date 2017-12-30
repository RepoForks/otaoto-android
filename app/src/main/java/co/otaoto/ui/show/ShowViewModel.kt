package co.otaoto.ui.show

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import co.otaoto.api.ApiClient
import co.otaoto.api.ShowError
import co.otaoto.api.ShowException
import co.otaoto.api.ShowSuccess
import co.otaoto.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class ShowViewModel(private val apiClient: ApiClient, private val pathSegments: List<String>) : BaseViewModel(), ShowContract.ViewModel {
    class Factory @Inject constructor(
            private val apiClient: ApiClient,
            private val pathSegments: List<String>
    ) : BaseViewModel.Factory<ShowContract.ViewModel>() {
        override fun create(): ShowContract.ViewModel = ShowViewModel(apiClient, pathSegments)
    }

    private val slug: String?
    private val key: String?

    private val _state = MutableLiveData<ShowContract.State>()
    override val state: LiveData<ShowContract.State>
        get() = _state
    private val _secret = MutableLiveData<String>()
    override val secret: LiveData<String>
        get() = _secret
    private val _moveToCreateTrigger = MutableLiveData<Unit>()
    override val moveToCreateTrigger: LiveData<Unit>
        get() = _moveToCreateTrigger

    init {
        if (pathSegments.size == 3) {
            slug = pathSegments[1]
            key = pathSegments[2]
            val pathState: ShowContract.State = ShowContract.State.values().find { it.path == pathSegments[0] } ?: ShowContract.State.GONE
            _state.value = if (pathState == ShowContract.State.SHOW) ShowContract.State.GATE else pathState // Never show on launch
        } else {
            slug = null
            key = null
            _state.value = ShowContract.State.GONE
        }
    }

    override fun clickCreateAnother() {
        _moveToCreateTrigger.value = Unit
    }

    override suspend fun clickReveal() = withLoadingDialog {
        if (_state.value != ShowContract.State.GATE || slug == null || key == null) return@withLoadingDialog
        val result = apiClient.show(slug, key)
        return@withLoadingDialog when (result) {
            is ShowSuccess -> {
                _secret.value = result.plainText
                _state.value = ShowContract.State.SHOW
            }
            is ShowError -> {
                Timber.e("An error occurred on show: %s", result.error)
                _secret.value = null
                _state.value = ShowContract.State.GONE
            }
            is ShowException -> {
                Timber.e(result.exception, "An exception was thrown on show")
                _secret.value = null
                _state.value = ShowContract.State.GONE
            }
        }
    }
}
