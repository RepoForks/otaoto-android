package co.otaoto.ui.create

import android.arch.lifecycle.LiveData
import co.otaoto.ui.base.BaseContract

interface CreateContract {
    interface View : BaseContract.View

    interface ViewModel : BaseContract.ViewModel {
        suspend fun submit(secret: String)
        fun reportPasswordVisibleHackComplete()
        val moveToConfirmTrigger: LiveData<SecretData>
        val errorTrigger: LiveData<Throwable>
        val passwordVisibleHackTrigger: LiveData<Unit>
    }

    data class SecretData(val secret: String, val slug: String, val key: String)
}
