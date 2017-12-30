package co.otaoto.ui.show

import android.arch.lifecycle.LiveData
import co.otaoto.ui.base.BaseContract

interface ShowContract {
    interface View : BaseContract.View {
        fun renderGate()
        fun renderShow()
        fun renderGone()
    }

    interface ViewModel : BaseContract.ViewModel {
        suspend fun clickReveal()
        fun clickCreateAnother()
        val state: LiveData<State>
        val secret: LiveData<String>
        val moveToCreateTrigger: LiveData<Unit>
    }

    enum class State(val path: String, val render: ShowContract.View.() -> Unit) {
        GATE("gate", ShowContract.View::renderGate),
        SHOW("show", ShowContract.View::renderShow),
        GONE("gone", ShowContract.View::renderGone);
    }
}
