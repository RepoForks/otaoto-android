package co.otaoto.secret

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class SecretViewModel : ViewModel() {
    internal interface View {
        fun performPasswordVisibleHack(): Unit
    }

    private val hasPerformedPasswordVisibleHack = MutableLiveData<Boolean>()

    internal fun checkPasswordVisibleHack(view: View) {
        if (hasPerformedPasswordVisibleHack.value ?: false) return
        hasPerformedPasswordVisibleHack.value = true
        view.performPasswordVisibleHack()
    }

}
