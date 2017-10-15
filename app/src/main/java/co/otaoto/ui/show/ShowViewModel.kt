package co.otaoto.ui.show

import android.arch.lifecycle.MutableLiveData
import co.otaoto.api.Api
import co.otaoto.api.ShowError
import co.otaoto.api.ShowSuccess
import co.otaoto.ui.base.BaseViewModel
import javax.inject.Inject

class ShowViewModel(private val api: Api, pathSegments: List<String>) : BaseViewModel<ShowContract.View>(), ShowContract.ViewModel {
    class Factory @Inject constructor(
            private val api: Api,
            private val pathSegments: List<String>
    ) : BaseViewModel.Factory<ShowViewModel>() {
        override fun create(): ShowViewModel = ShowViewModel(api, pathSegments)
    }

    private enum class State(val path: String, val render: ShowContract.View.() -> Unit) {
        GATE("gate", ShowContract.View::renderGate),
        SHOW("show", ShowContract.View::renderShow),
        GONE("gone", ShowContract.View::renderGone);
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

    override fun init(view: ShowContract.View) {
        super.init(view)
        view.observe(state) { it?.render?.invoke(this) }
        view.observe(secret) { showSecret(it ?: "") }
        view.observe(moveToCreateTrigger) { moveToCreateScreen() }
    }

    override fun clickCreateAnother() {
        moveToCreateTrigger.value = Unit
    }

    override suspend fun clickReveal() {
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
