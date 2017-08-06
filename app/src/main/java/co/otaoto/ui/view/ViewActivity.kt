package co.otaoto.ui.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import butterknife.ButterKnife
import butterknife.OnClick
import co.otaoto.R
import co.otaoto.ui.secret.SecretActivity

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.view_create_button)
    internal fun onCreateClick() {
        startActivity(Intent(this, SecretActivity::class.java))
        finish()
    }
}
