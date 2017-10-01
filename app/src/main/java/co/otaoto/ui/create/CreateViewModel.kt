package co.otaoto.ui.create

import co.otaoto.api.Api
import co.otaoto.api.CreateError
import co.otaoto.api.CreateSuccess
import co.otaoto.ui.base.BaseViewModel
import javax.inject.Inject

class CreateViewModel(val api: Api) : BaseViewModel<CreateViewModel.View>() {
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

    override fun init(view: View) {
        super.init(view)
        if (hasPerformedPasswordVisibleHack) return
        hasPerformedPasswordVisibleHack = true
        view.performPasswordVisibleHack()
    }

    internal suspend fun submit(view: View, secret: String) {
        loadingDialogVisible.value = true
        val result = api.create(secret)
        loadingDialogVisible.value = false
        return when (result) {
            is CreateSuccess -> view.moveToConfirmScreen(secret, result.slug, result.key)
            is CreateError -> view.showError()
        }
    }

}
