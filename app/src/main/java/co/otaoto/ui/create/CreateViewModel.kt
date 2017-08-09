package co.otaoto.ui.create

import co.otaoto.api.CreateError
import co.otaoto.api.CreateSuccess
import co.otaoto.injector.API
import co.otaoto.ui.base.BaseViewModel

class CreateViewModel : BaseViewModel<CreateViewModel.View>() {
    interface View : BaseViewModel.View {
        fun moveToConfirmScreen(secret: String, slug: String, key: String)
        fun showError()
        fun performPasswordVisibleHack()
    }

    private var hasPerformedPasswordVisibleHack = false

    internal fun init(view: View) {
        if (hasPerformedPasswordVisibleHack) return
        hasPerformedPasswordVisibleHack = true
        view.performPasswordVisibleHack()
    }

    internal suspend fun submit(view: View, secret: String) {
        val result = API.create(secret)
        return when (result) {
            is CreateSuccess -> view.moveToConfirmScreen(secret, result.slug, result.key)
            is CreateError -> view.showError()
        }
    }

}
