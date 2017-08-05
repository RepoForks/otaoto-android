package co.otaoto.secret

import android.arch.lifecycle.ViewModel
import co.otaoto.api.CreateError
import co.otaoto.api.CreateSuccess
import co.otaoto.injector.API

class SecretViewModel : ViewModel() {
    internal interface View {
        fun moveToConfirmScreen()
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
        when (result) {
            is CreateSuccess -> view.moveToConfirmScreen()
            is CreateError -> view.showError()
        }
    }

}
