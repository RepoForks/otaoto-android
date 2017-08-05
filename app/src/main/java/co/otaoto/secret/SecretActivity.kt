package co.otaoto.secret

import android.arch.lifecycle.LifecycleActivity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import co.otaoto.ConfirmActivity
import co.otaoto.R
import co.otaoto.bindView
import co.otaoto.bindViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class SecretActivity : LifecycleActivity(), SecretViewModel.View {
    private val viewModel by bindViewModel(SecretViewModel::class.java)

    private val inputLayout: TextInputLayout by bindView(R.id.secret_input_layout)
    private val inputTextView: TextView by bindView(R.id.secret_input_edittext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)
        ButterKnife.bind(this)
        viewModel.init(this)
    }

    override fun moveToConfirmScreen() {
        startActivity(Intent(this, ConfirmActivity::class.java))
        finish()
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
    }

    override fun performPasswordVisibleHack() {
        // h4x! We want to start in visible password mode and there doesn't seem to be a default to do this.
        inputLayout.post { inputLayout.findViewById<View>(R.id.text_input_password_toggle).callOnClick() }
    }

    @OnClick(R.id.secret_submit_button)
    internal fun onSubmitClick() {
        async(UI) {
            val text = inputTextView.text ?: ""
            viewModel.submit(this@SecretActivity, text.toString())
        }
    }
}
