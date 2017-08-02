package co.otaoto

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import butterknife.ButterKnife
import butterknife.OnClick

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.view_create_button)
    internal fun onCreateClick() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
