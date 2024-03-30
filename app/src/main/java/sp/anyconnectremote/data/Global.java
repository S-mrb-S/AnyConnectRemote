package sp.anyconnectremote.data;

import android.app.Application;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import sp.anyconnectremote.AppConfig;
import sp.anyconnectremote.model.LogManager;
import sp.anyconnectremote.model.MainViewModel;
import sp.anyconnectremote.util.MmkvManager;

// I need some object ;-)
public class Global extends GlobalHelper {
    public Global(Application context) {
        setMainApplication(context);
    }

    @NonNull
    public LogManager getLogManager() {
        if (this.logManager == null) {
            this.logManager = new LogManager();
        }
        return this.logManager;
    }

    @NonNull
    public MainViewModel getmViewModel() {
        if (this.mViewModel == null) {
            this.mViewModel =
                    new ViewModelProvider.AndroidViewModelFactory((Application) getMainApplication().getApplicationContext()).create(MainViewModel.class);
        }
        return this.mViewModel;
    }

    @NonNull
    public MmkvManager getMmkvStorage() {
        if (mmkvStorage == null) {
            mmkvStorage = new MmkvManager();
        }
        return mmkvStorage;
    }

    @NonNull
    public Application getMainApplication() {
        assert this.mainApplication != null;
        return this.mainApplication;
    }

    public void setMainApplication(@NonNull Application mainApplication) {
        this.mainApplication = mainApplication;
    }

    public boolean isImportantErrorBoolean() {
        return this.isImportantErrorBoolean;
    }

    public void setImportantErrorBoolean(boolean importantErrorBoolean) {
        this.isImportantErrorBoolean = importantErrorBoolean;
    }

    /**
     * Java +17
     *
     * @param appInstalled
     * @param permissionsEnabled
     */
    public record CiscoInstallationStatus(boolean appInstalled, boolean permissionsEnabled) {
    }

    public CiscoInstallationStatus getCiscoInstallationStatus() {
        PackageManager packageManager = this.getMainApplication().getPackageManager();
        boolean appInstalled = isAppInstalled(packageManager);
        boolean permissionsEnabled = isPermissionGranted(packageManager);
        return new CiscoInstallationStatus(appInstalled, permissionsEnabled);
    }

    private boolean isAppInstalled(PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(AppConfig.ciscoPackageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean isPermissionGranted(PackageManager packageManager) {
        int result = packageManager.checkPermission("android.permission.POST_NOTIFICATIONS", AppConfig.ciscoPackageName);
        this.getLogManager().saveLog("isPermissionsEnabled: " + result);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Global
     *
     * @param msg Text message
     */
    public void showToast(String msg) {
        try {
            Toast.makeText(this.getMainApplication(), msg, Toast.LENGTH_LONG).show();
            this.getLogManager().saveLog("(toast) msg: " + msg);
        } catch (Exception e) {
            this.getLogManager().logCat("showToast is broken: " + e, true);
        }
    }
}