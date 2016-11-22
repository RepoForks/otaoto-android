package co.otaoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.secret_input_layout)
    TextInputLayout secretInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // h4x! We want to start in visible password mode and there doesn't seem to be a default to do this.
        secretInputLayout.post(new Runnable() {
            @Override
            public void run() {
                secretInputLayout.findViewById(R.id.text_input_password_toggle).performClick();
            }
        });
    }

    @OnClick(R.id.secret_submit_button)
    void onSubmitClick() {
        startActivity(new Intent(this, ConfirmActivity.class));
        finish();
    }
}
