package sp.anyconnectremote.ui.misc;

import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import sp.anyconnectremote.data.Global;
import sp.anyconnectremote.data.Static;

public class BaseActivity extends AppCompatActivity {
    protected final BaseActivityResult<Intent, ActivityResult> activityLauncher =
            BaseActivityResult.registerActivityForResult(this);

    protected final Global data = Static.getGlobalData();

    protected void goToBack() {
        this.getOnBackPressedDispatcher().onBackPressed();
    }

    protected void finishAllTask() {
        this.finishAffinity();
    }

    protected void finishThisTask() {
        this.finish();
    }
}