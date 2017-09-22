package co.otaoto.ui.show

import android.content.Intent
import android.os.Bundle
import android.support.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import co.otaoto.R
import co.otaoto.api.Api
import co.otaoto.ui.base.BaseActivity
import co.otaoto.ui.bindView
import co.otaoto.ui.bindViewModel
import co.otaoto.ui.create.CreateActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class ShowActivity : BaseActivity<ShowViewModel, ShowViewModel.View>(), ShowViewModel.View {
    private val viewModel by bindViewModel(ShowViewModel::class.java)

    internal val rootView: ViewGroup by bindView(R.id.activity_show)
    private val secretTextView: TextView by bindView(R.id.show_secret_text)
    private val revealButton: Button by bindView(R.id.show_reveal_button)

    @Inject
    lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        ButterKnife.bind(this)
        val pathSegments = intent.data.pathSegments
        viewModel.init(this, pathSegments, api)
    }

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
    internal fun onRevealClick() {
        launch(UI) {
            viewModel.clickReveal(this@ShowActivity)
        }
    }

    @OnClick(R.id.show_create_button)
    internal fun onCreateClick() {
        viewModel.clickCreateAnother(this)
    }
}
