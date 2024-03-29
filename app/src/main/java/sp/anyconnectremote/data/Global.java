package sp.anyconnectremote.data;

import android.app.Application;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import sp.anyconnectremote.model.LogManager;
import sp.anyconnectremote.model.MainViewModel;
import sp.anyconnectremote.util.MmkvManager;

// I need some object ;-)
public class Global {
    @NonNull
    private final String defaultLogString = "** ** ** **";
    @NonNull
    private final String ciscoPackageName = "com.cisco.anyconnect.vpn.android.avf"; // += AndroidManifest

    private LogManager logManager;
    private MainViewModel mViewModel;
    private MmkvManager mmkvStorage;

    @Nullable
    private Application mainApplication;

    private boolean isImportantErrorBoolean = false;

    public Global(Application context) {
        setMainApplication(context);
    }

    @NonNull
    public String getDefaultLogString() {
        return this.defaultLogString;
    }

    @NonNull
    public String getCiscoPackageName() {
        return this.ciscoPackageName;
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

    //    java17
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
            packageManager.getPackageInfo(this.ciscoPackageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    //    private boolean isPermissionsEnabled(PackageManager packageManager) {
//        try {
//            PackageInfo packageInfo = packageManager.getPackageInfo(this.ciscoPackageName, PackageManager.GET_PERMISSIONS);
//            if (packageInfo == null) {
//                this.getLogManager().saveLog("isPermissionsEnabled: empty");
//                return false;
//            }
//            String[] requestedPermissions = packageInfo.requestedPermissions;
//            this.getLogManager().saveLog("isPermissionsEnabled: " + Arrays.toString(requestedPermissions));
//            return requestedPermissions != null && requestedPermissions.length > 0;
//        } catch (PackageManager.NameNotFoundException ex) {
//            return false;
//        }
//    }
    private boolean isPermissionGranted(PackageManager packageManager) {
        int result = packageManager.checkPermission("android.permission.POST_NOTIFICATIONS", this.ciscoPackageName);
        this.getLogManager().saveLog("isPermissionsEnabled: " + result);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void showToast(String msg) {
        try {
            Toast.makeText(getMainApplication(), msg, Toast.LENGTH_LONG).show();
            getLogManager().saveLog("(toast) msg: " + msg);
        } catch (Exception e) {
            getLogManager().logCat("showToast is broken: " + e, true);
        }
    }
}