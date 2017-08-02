package co.otaoto

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick

class ConfirmActivity : AppCompatActivity() {

    val rootView: ViewGroup by bindView(R.id.activity_confirm)
    val secretTextView: TextView by bindView(R.id.confirm_secret_text)
    val linkTextView: TextView by bindView(R.id.confirm_link_text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.confirm_link_text)
    internal fun onLinkClick() {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText(getString(R.string.link_clipboard_label), linkTextView.text.toString())
    }

    @OnCheckedChanged(R.id.confirm_show_secret_toggle)
    internal fun onShowSecretCheckedChanged(checked: Boolean) {
        secretTextView.post {
            TransitionManager.beginDelayedTransition(rootView)
            secretTextView.visibility = if (checked) View.VISIBLE else View.GONE
        }
    }
}
