package co.otaoto

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.view.View

import butterknife.ButterKnife
import butterknife.OnClick

class MainActivity : AppCompatActivity() {
    private val secretInputLayout: TextInputLayout by bindView(R.id.secret_input_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        // h4x! We want to start in visible password mode and there doesn't seem to be a default to do this.
        secretInputLayout.post { secretInputLayout.findViewById<View>(R.id.text_input_password_toggle).callOnClick() }
    }

    @OnClick(R.id.secret_submit_button)
    internal fun onSubmitClick() {
        startActivity(Intent(this, ConfirmActivity::class.java))
        finish()
    }
}
