package co.otaoto.ui.show

import android.content.Intent
import android.support.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.OnClick
import co.otaoto.R
import co.otaoto.ui.base.BaseActivity
import co.otaoto.ui.bindView
import co.otaoto.ui.create.CreateActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class ShowActivity : BaseActivity<ShowViewModel, ShowContract.View>(), ShowContract.View {

    @Inject
    override lateinit var viewModelFactory: ShowViewModel.Factory
    override val viewModelClass get() = ShowViewModel::class.java

    internal val rootView: ViewGroup by bindView(R.id.activity_show)
    private val secretTextView: TextView by bindView(R.id.show_secret_text)
    private val revealButton: Button by bindView(R.id.show_reveal_button)

    override val layoutRes: Int get() = R.layout.activity_show

    override fun renderGate() {
        TransitionManager.beginDelayedTransition(rootView)
        revealButton.isEnabled = true
        secretTextView.visibility = View.GONE
        secretTextView.text = ""
    }

    override fun renderShow() {
        TransitionManager.beginDelayedTransition(rootView)
        revealButton.isEnabled = false
        secretTextView.visibility = View.VISIBLE
    }

    override fun renderGone() {
        TransitionManager.beginDelayedTransition(rootView)
        revealButton.isEnabled = false
        secretTextView.visibility = View.VISIBLE
        secretTextView.setText(R.string.show_gone)
    }

    override fun showSecret(secret: String) {
        secretTextView.text = secret
    }

    override fun moveToCreateScreen() {
        startActivity(Intent(this, CreateActivity::class.java))
        finish()
    }

    @OnClick(R.id.show_reveal_button)
    protected fun onRevealClick() {
        launch(UI) {
            viewModel.clickReveal()
        }
    }

    @OnClick(R.id.show_create_button)
    protected fun onCreateClick() = viewModel.clickCreateAnother()
}
