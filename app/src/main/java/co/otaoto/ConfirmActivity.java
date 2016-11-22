package co.otaoto;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class ConfirmActivity extends AppCompatActivity {

    @BindView(R.id.activity_confirm)
    ViewGroup rootView;

    @BindView(R.id.confirm_secret_text)
    TextView secretTextView;

    @BindView(R.id.confirm_link_text)
    TextView linkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @OnClick(R.id.confirm_link_text)
    void onLinkClick() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(getString(R.string.link_clipboard_label),
                                                              linkTextView.getText().toString()));
    }

    @OnCheckedChanged(R.id.confirm_show_secret_toggle)
    void onShowSecretCheckedChanged(final boolean checked) {
        secretTextView.post(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(rootView);
                secretTextView.setVisibility(checked ? View.VISIBLE : View.GONE);
            }
        });
    }
}
