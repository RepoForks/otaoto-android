package co.otaoto.ui.create

import android.arch.lifecycle.MutableLiveData
import co.otaoto.api.Api
import co.otaoto.api.CreateError
import co.otaoto.api.CreateSuccess
import co.otaoto.ui.base.BaseViewModel
import co.otaoto.ui.create.CreateViewModel.View
import javax.inject.Inject

class CreateViewModel(val api: Api) : BaseViewModel<View>() {
    interface View : BaseViewModel.View {
        fun moveToConfirmScreen(secret: String, slug: String, key: String)
        fun showError()
        fun performPasswordVisibleHack()
    }

    class Factory @Inject constructor() : BaseViewModel.Factory<CreateViewModel>() {
        @Inject
        protected lateinit var api: Api

        override fun create(): CreateViewModel = CreateViewModel(api)
    }

    private var hasPerformedPasswordVisibleHack = false

    private val moveToConfirmTrigger = MutableLiveData<SecretData>()
    private val errorTrigger = MutableLiveData<Unit>()

    override fun init(view: View) {
        super.init(view)
        if (!hasPerformedPasswordVisibleHack) {
            hasPerformedPasswordVisibleHack = true
            view.performPasswordVisibleHack()
        }
        view.observe(moveToConfirmTrigger) {
            moveToConfirmScreen(it.secret, it.slug, it.key)
        }
        view.observe(errorTrigger) {
            showError()
        }
    }

    internal suspend fun submit(secret: String) {
        loadingDialogVisible.value = true
        val result = api.create(secret)
        loadingDialogVisible.value = false
        return when (result) {
            is CreateSuccess -> moveToConfirmTrigger.value = SecretData(secret, result.slug, result.key)
            is CreateError -> errorTrigger.value = Unit
        }
    }

    private data class SecretData(val secret: String, val slug: String, val key: String)
}
