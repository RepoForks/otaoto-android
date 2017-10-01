package co.otaoto.ui.show

import android.arch.lifecycle.MutableLiveData
import co.otaoto.api.Api
import co.otaoto.api.ShowError
import co.otaoto.api.ShowSuccess
import co.otaoto.ui.base.BaseViewModel
import javax.inject.Inject

class ShowViewModel(private val api: Api, pathSegments: List<String>) : BaseViewModel<ShowViewModel.View>() {
    interface View : BaseViewModel.View {
        fun renderGate()
        fun renderShow()
        fun renderGone()
        fun showSecret(secret: String)
        fun moveToCreateScreen()
    }

    class Factory @Inject constructor() : BaseViewModel.Factory<ShowViewModel>() {
        @Inject
        protected lateinit var api: Api

        @Inject
        protected lateinit var pathSegments: List<String>

        override fun create(): ShowViewModel = ShowViewModel(api, pathSegments)
    }

    enum class State(val path: String, val render: View.() -> Unit) {
        GATE("gate", View::renderGate),
        SHOW("show", View::renderShow),
        GONE("gone", View::renderGone);
    }

    private val slug: String?
    private val key: String?

    private val state = MutableLiveData<State>()
    private val secret = MutableLiveData<String?>()
    private val moveToCreateTrigger = MutableLiveData<Unit>()

    init {
        if (pathSegments.size == 3) {
            slug = pathSegments[1]
            key = pathSegments[2]
            val pathState: State = State.values().find { it.path == pathSegments[0] } ?: State.GONE
            state.value = if (pathState == State.SHOW) State.GATE else pathState // Never show on launch
        } else {
            slug = null
            key = null
            state.value = State.GONE
        }
    }

    override fun init(view: View) {
        super.init(view)
        view.observe(state) { it?.render?.invoke(this) }
        view.observe(secret) { showSecret(it ?: "") }
        view.observe(moveToCreateTrigger) { moveToCreateScreen() }
    }

    internal fun clickCreateAnother() {
        moveToCreateTrigger.value = Unit
    }

    internal suspend fun clickReveal() {
        if (state.value != State.GATE || slug == null || key == null) return
        loadingDialogVisible.value = true
        val result = api.show(slug, key)
        loadingDialogVisible.value = false
        return when (result) {
            is ShowSuccess -> {
                secret.value = result.plainText
                state.value = State.SHOW
            }
            is ShowError -> {
                secret.value = null
                state.value = State.GONE
            }
        }
    }
}
