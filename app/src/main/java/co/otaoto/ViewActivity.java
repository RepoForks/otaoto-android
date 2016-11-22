package co.otaoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.view_create_button)
    void onCreateClick() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
