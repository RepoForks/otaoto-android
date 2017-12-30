package co.otaoto.ui.create

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import co.otaoto.api.ApiClient
import co.otaoto.api.CreateError
import co.otaoto.api.CreateSuccess
import co.otaoto.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class CreateViewModel(private val apiClient: ApiClient) : BaseViewModel(), CreateContract.ViewModel {
    class Factory @Inject constructor(private val apiClient: ApiClient) : BaseViewModel.Factory<CreateContract.ViewModel>() {
        override fun create(): CreateContract.ViewModel = CreateViewModel(apiClient)
    }

    private val _moveToConfirmTrigger = MutableLiveData<CreateContract.SecretData>()
    override val moveToConfirmTrigger: LiveData<CreateContract.SecretData>
        get() = _moveToConfirmTrigger

    private val _errorTrigger = MutableLiveData<Throwable>()
    override val errorTrigger: LiveData<Throwable>
        get() = _errorTrigger

    private val _passwordVisibleHackTrigger = MutableLiveData<Unit>()
    override val passwordVisibleHackTrigger: LiveData<Unit>
        get() = _passwordVisibleHackTrigger

    init {
        _passwordVisibleHackTrigger.value = Unit
    }

    override fun reportPasswordVisibleHackComplete() {
        _passwordVisibleHackTrigger.value = null
    }

    override suspend fun submit(secret: String) = withLoadingDialog {
        val result = apiClient.create(secret)
        return@withLoadingDialog when (result) {
            is CreateSuccess -> _moveToConfirmTrigger.value = CreateContract.SecretData(secret, result.slug, result.key)
            is CreateError -> {
                Timber.e(result.exception, "An exception was thrown on create")
                _errorTrigger.value = result.exception
            }
        }
    }

}
