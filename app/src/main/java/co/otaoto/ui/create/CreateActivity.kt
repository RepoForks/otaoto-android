package co.otaoto.ui.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.TextView
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import co.otaoto.R
import co.otaoto.ui.base.BaseActivity
import co.otaoto.ui.bindView
import co.otaoto.ui.bindViewModel
import co.otaoto.ui.confirm.ConfirmActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CreateActivity : BaseActivity<CreateViewModel, CreateViewModel.View>(), CreateViewModel.View {
    companion object {
        fun newIntent(context: Context) = Intent(context, CreateActivity::class.java)
    }

    private val viewModel by bindViewModel(CreateViewModel::class.java)

    private val inputLayout: TextInputLayout by bindView(R.id.create_input_layout)
    private val inputTextView: TextView by bindView(R.id.create_input_edittext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        ButterKnife.bind(this)
        viewModel.init(this)
    }

    override fun moveToConfirmScreen(secret: String, slug: String, key: String) {
        startActivity(ConfirmActivity.newIntent(this, secret, slug, key))
        finish()
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.generic_error), Toast.LENGTH_SHORT).show()
    }

    override fun performPasswordVisibleHack() {
        // h4x! We want to start in visible password mode and there doesn't seem to be a default to do this.
        inputLayout.post { inputLayout.findViewById<View>(R.id.text_input_password_toggle).callOnClick() }
    }

    @OnClick(R.id.create_submit_button)
    internal fun onSubmitClick() {
        async(UI) {
            val text = inputTextView.text ?: ""
            viewModel.submit(this@CreateActivity, text.toString())
        }
    }
}
