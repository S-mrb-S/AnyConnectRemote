package sp.anyconnectremote.ui.misc;

import android.content.Intent;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import sp.anyconnectremote.util.LogManager;

public class BaseActivity extends AppCompatActivity {
    protected final BaseActivityResult<Intent, ActivityResult> activityLauncher =
            BaseActivityResult.registerActivityForResult(this);

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        LogManager.saveLog("(toast) msg: " + msg);
    }
}
