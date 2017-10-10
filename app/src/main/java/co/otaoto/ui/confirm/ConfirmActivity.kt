package co.otaoto.ui.confirm

import android.content.Context
import android.content.Intent
import android.support.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.OnCheckedChanged
import butterknife.OnClick
import co.otaoto.R
import co.otaoto.ui.base.BaseActivity
import co.otaoto.ui.bindView
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_KEY
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SECRET
import co.otaoto.ui.confirm.ConfirmContract.Companion.PARAM_SLUG
import co.otaoto.ui.create.CreateActivity
import javax.inject.Inject

class ConfirmActivity : BaseActivity<ConfirmViewModel, ConfirmContract.View>(), ConfirmContract.View {
    companion object {
        fun newIntent(context: Context, secret: String, slug: String, key: String): Intent =
                Intent(context, ConfirmActivity::class.java)
                        .putExtra(PARAM_SECRET, secret)
                        .putExtra(PARAM_SLUG, slug)
                        .putExtra(PARAM_KEY, key)
    }

    @Inject
    override lateinit var viewModelFactory: ConfirmViewModel.Factory
    override val viewModelClass get() = ConfirmViewModel::class.java

    internal val rootView: ViewGroup by bindView(R.id.activity_confirm)
    private val secretTextView: TextView by bindView(R.id.confirm_secret_text)
    private val linkTextView: TextView by bindView(R.id.confirm_link_text)

    override val layoutRes: Int get() = R.layout.activity_confirm

    override fun showSecret() = animateSecretVisibility(View.VISIBLE)

    override fun hideSecret() = animateSecretVisibility(View.GONE)

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

    override fun shareUrl(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
                .setType("text/*")
                .putExtra(Intent.EXTRA_TEXT, url)
        val chooser = Intent.createChooser(intent, getString(R.string.label_share_url))
        startActivity(chooser)
    }

    override fun moveToCreateScreen() {
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
