package sp.anyconnectremote.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import sp.anyconnectremote.R;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LogFragment.newInstance())
                    .commitNow();
        }
    }
}