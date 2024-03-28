package sp.anyconnectremote.data;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import sp.anyconnectremote.MainApplication;
import sp.anyconnectremote.model.LogManager;
import sp.anyconnectremote.model.MainViewModel;

// I need some object ;-)
public class Global {
    @NonNull
    private final String defaultLogString = "** ** ** **";
    @NonNull
    private final String ciscoPackageName = "com.cisco.anyconnect.vpn.android.avf"; // += AndroidManifest
    @NonNull
    private final LogManager logManager;
    @NonNull
    private final MainViewModel mViewModel;

    @Nullable
    private MainApplication mainApplication;

    private boolean isCiscoInstalled = false;

    private boolean isImportantErrorBoolean = false;

    public Global() {
        logManager = new LogManager();
        mViewModel =
                new ViewModelProvider.AndroidViewModelFactory((Application) getMainApplication().getApplicationContext()).create(MainViewModel.class);
    }

    @NonNull
    public String getDefaultLogString() {
        return defaultLogString;
    }

    @NonNull
    public String getCiscoPackageName() {
        return ciscoPackageName;
    }

    @NonNull
    public LogManager getLogManager() {
        return logManager;
    }

    @NonNull
    public MainViewModel getmViewModel() {
        return mViewModel;
    }

    @NonNull
    public MainApplication getMainApplication() {
        assert mainApplication != null;
        return mainApplication;
    }

    public void setMainApplication(@NonNull MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public boolean isImportantErrorBoolean() {
        return isImportantErrorBoolean;
    }

    public void setImportantErrorBoolean(boolean importantErrorBoolean) {
        isImportantErrorBoolean = importantErrorBoolean;
    }

    /**
     * @return Cisco package
     */
    public boolean isCiscoInstalled() {
        return isCiscoInstalled;
    }

    public void setCiscoInstalled(boolean ciscoInstalled) {
        isCiscoInstalled = ciscoInstalled;
    }

    public void showToast(String msg) {
        try {
            Toast.makeText(getMainApplication(), msg, Toast.LENGTH_LONG).show();
            getLogManager().saveLog("(toast) msg: " + msg);
        } catch (Exception e) {
            logManager.logCat("showToast is broken: " + e, true);
        }
    }
}