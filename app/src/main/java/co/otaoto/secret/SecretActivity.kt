package co.otaoto.secret

import android.arch.lifecycle.LifecycleActivity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View

import butterknife.ButterKnife
import butterknife.OnClick
import co.otaoto.ConfirmActivity
import co.otaoto.R
import co.otaoto.bindView
import co.otaoto.bindViewModel

class SecretActivity : LifecycleActivity(), SecretViewModel.View {
    private val viewModel by bindViewModel(SecretViewModel::class.java)

    private val inputLayout: TextInputLayout by bindView(R.id.secret_input_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)
        ButterKnife.bind(this)

        viewModel.checkPasswordVisibleHack(this)
    }

    override fun performPasswordVisibleHack() {
        // h4x! We want to start in visible password mode and there doesn't seem to be a default to do this.
        inputLayout.post { inputLayout.findViewById<View>(R.id.text_input_password_toggle).callOnClick() }
    }

    @OnClick(R.id.secret_submit_button)
    internal fun onSubmitClick() {
        startActivity(Intent(this, ConfirmActivity::class.java))
        finish()
    }
}
