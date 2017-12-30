package co.otaoto.ui.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.OnCheckedChanged
import butterknife.OnClick
import co.otaoto.R
import co.otaoto.ui.base.BaseActivity
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_KEY
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SECRET
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SLUG
import co.otaoto.ui.create.CreateActivity
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : BaseActivity<ConfirmContract.ViewModel, ConfirmViewModel.Factory>(), ConfirmContract.View {
    companion object {
        fun newIntent(context: Context, secret: String, slug: String, key: String): Intent =
                Intent(context, ConfirmActivity::class.java)
                        .putExtra(PARAM_SECRET, secret)
                        .putExtra(PARAM_SLUG, slug)
                        .putExtra(PARAM_KEY, key)
    }

    internal val rootView: ViewGroup inline get() = activity_confirm
    private val secretTextView: TextView inline get() = confirm_secret_text
    private val linkTextView: TextView inline get() = confirm_link_text

    override val layoutRes: Int get() = R.layout.activity_confirm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.run {
            url.observeNonNull { setLinkUrl(it) }
            secretVisible.observe { visible ->
                val secret = secretValue.value ?: ""
                if (visible == true) {
                    setSecretText(secret)
                    showSecret()
                } else {
                    hideSecret()
                    setSecretText("")
                }

            }
            shareTrigger.observeNonNull {
                url.value?.let { shareUrl(it) }
            }
            moveToCreateTrigger.observeNonNull {
                moveToCreateScreen()
            }
        }
    }

    private fun showSecret() = animateSecretVisibility(View.VISIBLE)

    private fun hideSecret() = animateSecretVisibility(View.GONE)

    private fun animateSecretVisibility(visibility: Int) {
        secretTextView.post {
            TransitionManager.beginDelayedTransition(rootView)
            secretTextView.visibility = visibility
        }
    }

    private fun setSecretText(text: String) {
        secretTextView.text = text
    }

    private fun setLinkUrl(url: String) {
        linkTextView.text = url
    }

    private fun shareUrl(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
                .setType("text/*")
                .putExtra(Intent.EXTRA_TEXT, url)
        val chooser = Intent.createChooser(intent, getString(R.string.label_share_url))
        startActivity(chooser)
    }

    private fun moveToCreateScreen() {
        startActivity(CreateActivity.newIntent(this))
        finish()
    }

    @OnClick(R.id.confirm_link_text)
    protected fun onLinkClick() = viewModel.clickLink()

    @OnCheckedChanged(R.id.confirm_show_secret_toggle)
    protected fun onShowSecretCheckedChanged(checked: Boolean) = viewModel.setSecretVisible(checked)

    @OnClick(R.id.confirm_create_another_button)
    protected fun onCreateAnotherClick() = viewModel.clickCreateAnother()
}
