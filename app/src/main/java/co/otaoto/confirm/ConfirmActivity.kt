package co.otaoto.confirm

import android.arch.lifecycle.LifecycleActivity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import co.otaoto.R
import co.otaoto.bindView
import co.otaoto.bindViewModel
import co.otaoto.secret.SecretActivity

class ConfirmActivity : LifecycleActivity(), ConfirmViewModel.View {
    companion object {
        private const val EXTRA_SECRET = "secret"
        private const val EXTRA_SLUG = "slug"
        private const val EXTRA_KEY = "key"

        fun newIntent(context: Context, secret: String, slug: String, key: String): Intent {
            return Intent(context, ConfirmActivity::class.java)
                    .putExtra(EXTRA_SECRET, secret)
                    .putExtra(EXTRA_SLUG, slug)
                    .putExtra(EXTRA_KEY, key)
        }
    }

    private val viewModel by bindViewModel(ConfirmViewModel::class.java)

    internal val rootView: ViewGroup by bindView(R.id.activity_confirm)
    private val secretTextView: TextView by bindView(R.id.confirm_secret_text)
    private val linkTextView: TextView by bindView(R.id.confirm_link_text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        ButterKnife.bind(this)
        with(intent.extras) {
            viewModel.init(
                    view = this@ConfirmActivity,
                    secret = getString(EXTRA_SECRET),
                    slug = getString(EXTRA_SLUG),
                    key = getString(EXTRA_KEY))
        }
    }

    override fun showSecret() {
        animateSecretVisibility(View.VISIBLE)
    }

    override fun hideSecret() {
        animateSecretVisibility(View.GONE)
    }

    private fun animateSecretVisibility(visibility: Int) {
        secretTextView.post {
            TransitionManager.beginDelayedTransition(rootView)
            secretTextView.visibility = visibility
        }
    }

    override fun setSecretText(text: String) {
        secretTextView.text = text
    }

    override fun setLinkUrl(url: String) {
        linkTextView.text = url
    }

    override fun copyUrl(url: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText(getString(R.string.link_clipboard_label), url)
    }

    override fun moveToSecretScreen() {
        startActivity(SecretActivity.newIntent(this))
        finish()
    }

    @OnClick(R.id.confirm_link_text)
    internal fun onLinkClick() {
        viewModel.clickLink(this)
    }

    @OnCheckedChanged(R.id.confirm_show_secret_toggle)
    internal fun onShowSecretCheckedChanged(checked: Boolean) {
        viewModel.setSecretVisible(this, checked)
    }

    @OnClick(R.id.confirm_create_another_button)
    internal fun onCreateAnotherClick() {
        viewModel.clickCreateAnother(this)
    }
}
