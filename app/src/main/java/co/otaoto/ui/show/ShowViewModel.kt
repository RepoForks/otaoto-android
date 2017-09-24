package co.otaoto.ui.show

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
        internal lateinit var api: Api

        @Inject
        internal lateinit var pathSegments: List<String>

        override fun create(): ShowViewModel = ShowViewModel(api, pathSegments)
    }

    enum class State(val path: String, val render: View.() -> Unit) {
        GATE("gate", View::renderGate),
        SHOW("show", View::renderShow),
        GONE("gone", View::renderGone);
    }

    private val slug: String?
    private val key: String?

    private var state: State

    private var secret: String? = null

    init {
        if (pathSegments.size == 3) {
            slug = pathSegments[1]
            key = pathSegments[2]
            val pathState: State = State.values().find { it.path == pathSegments[0] } ?: State.GONE
            state = if (pathState == State.SHOW) State.GATE else pathState // Never show on launch
        } else {
            slug = null
            key = null
            state = State.GONE
        }
    }

    override fun init(view: View) {
        view.renderState()
    }

    internal fun clickCreateAnother(view: View) {
        view.moveToCreateScreen()
    }

    internal suspend fun clickReveal(view: View) {
        if (state != State.GATE || slug == null || key == null) return
        val result = api.show(slug, key)
        return when (result) {
            is ShowSuccess -> {
                val secret = result.plainText
                this.secret = secret
                view.showSecret(secret)
                state = State.SHOW
                view.renderState()
            }
            is ShowError -> {
                secret = null
                state = State.GONE
                view.renderState()
            }
        }
    }

    private fun View.renderState() = (state.render)()
}
