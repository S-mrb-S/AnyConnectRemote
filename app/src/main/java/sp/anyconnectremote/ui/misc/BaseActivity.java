package sp.anyconnectremote.ui.misc;

import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected final BaseActivityResult<Intent, ActivityResult> activityLauncher =
            BaseActivityResult.registerActivityForResult(this);

}
