package sp.anyconnectremote.data;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import sp.anyconnectremote.MainApplication;
import sp.anyconnectremote.model.LogManager;
import sp.anyconnectremote.model.MainViewModel;

// I need some object ;-)
public class Global {
    @NonNull
    public String defaultLogString = "** ** ** **";
    @NonNull
    public String ciscoPackageName = "com.cisco.anyconnect.vpn.android.avf"; // += AndroidManifest
    @Nullable
    public LogManager logManager;
    @Nullable
    public MainViewModel mViewModel;
    @Nullable
    public MainApplication mainApplication;

    public boolean isCiscoInstalled = false;

    public void showToast(String msg) {
        try {
            if (mainApplication != null && logManager != null) {
                Toast.makeText(mainApplication, msg, Toast.LENGTH_LONG).show();
                logManager.saveLog("(toast) msg: " + msg);
            }
        } catch (Exception e) {
            logManager.logCat("showToast is broken: " + e, true);
        }
    }
}