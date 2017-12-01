package co.otaoto.ui.create

import android.arch.lifecycle.MutableLiveData
import co.otaoto.api.ApiClient
import co.otaoto.api.CreateError
import co.otaoto.api.CreateSuccess
import co.otaoto.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class CreateViewModel(private val apiClient: ApiClient) : BaseViewModel<CreateContract.View>(), CreateContract.ViewModel {
    class Factory @Inject constructor(private val apiClient: ApiClient) : BaseViewModel.Factory<CreateViewModel>() {
        override fun create(): CreateViewModel = CreateViewModel(apiClient)
    }

    private var hasPerformedPasswordVisibleHack = false

    private val moveToConfirmTrigger = MutableLiveData<SecretData>()
    private val errorTrigger = MutableLiveData<Throwable>()

    override fun init(view: CreateContract.View) {
        super.init(view)
        if (!hasPerformedPasswordVisibleHack) {
            hasPerformedPasswordVisibleHack = true
            view.performPasswordVisibleHack()
        }
        view.observe(moveToConfirmTrigger) {
            moveToConfirmScreen(it.secret, it.slug, it.key)
        }
        view.observe(errorTrigger) {
            showError(it)
        }
    }

    override suspend fun submit(secret: String) {
        loadingDialogVisible.value = true
        val result = apiClient.create(secret)
        loadingDialogVisible.value = false
        return when (result) {
            is CreateSuccess -> moveToConfirmTrigger.value = SecretData(secret, result.slug, result.key)
            is CreateError -> {
                Timber.e(result.exception, "An exception was thrown on create")
                errorTrigger.value = result.exception
            }
        }
    }

    private data class SecretData(val secret: String, val slug: String, val key: String)
}
